package com.epam.esm.zotov.mjcschool.api.controller.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.esm.zotov.mjcschool.api.dto.ListDto;
import com.epam.esm.zotov.mjcschool.api.dto.OrderDto;
import com.epam.esm.zotov.mjcschool.api.dto.UserDto;
import com.epam.esm.zotov.mjcschool.api.exception.NoResourceFoundException;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.service.certificate.CertificateService;
import com.epam.esm.zotov.mjcschool.service.order.OrderService;
import com.epam.esm.zotov.mjcschool.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@Validated
public class UserControllerImpl implements UserController {
    private static final String ORDER_RELATION = "orders";
    private static final int DEFAULT_LIMIT = 10;
    private static final int DEFAULT_START_ID = 0;
    private UserService userService;
    private CertificateService certificateService;
    private OrderService orderService;

    @Autowired
    public UserControllerImpl(UserService userService, CertificateService certificateService,
            OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
        this.certificateService = certificateService;
    }

    @Override
    public ListDto<UserDto> getPage(int pageSize, int pageNumber) {
        List<User> users = userService.getPage(pageSize, pageNumber);
        if (Objects.isNull(users) || users.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            ListDto<UserDto> listDto = new ListDto<UserDto>(
                    users.stream().map(user -> new UserDto(user)).collect(Collectors.toList()));

            listDto.getList().stream().forEach(userDto -> addCommonUserHateoasLinks(userDto, pageSize));
            listDto.addNextPageLink(methodOn(UserControllerImpl.class).getPage(pageSize, ++pageNumber));
            listDto.addPreviousPageLink(methodOn(UserControllerImpl.class).getPage(pageSize, ++pageNumber));
            return listDto;
        }
    }

    @Override
    public UserDto getById(long targetId) {
        Optional<User> user = userService.getById(targetId);
        if (user.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            UserDto dto = new UserDto(user.get());
            addCommonUserHateoasLinks(dto, DEFAULT_LIMIT);
            return dto;
        }
    }

    @Override
    public OrderDto getOrder(long userId, int orderPosition) {
        Optional<User> user = userService.getById(userId);
        if (user.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            List<Order> orders = new ArrayList<Order>(user.get().getOrders());
            if (Objects.isNull(orders) || orders.isEmpty() || orders.size() >= orderPosition) {
                throw new NoResourceFoundException();
            } else {
                OrderDto dto = new OrderDto(orders.get(orderPosition));
                addCommonOrderHateoasLinks(dto, orderPosition);
                return dto;
            }
        }
    }

    @Override
    public ListDto<OrderDto> getUsersOrders(long userId, int startPosition, int limit) {
        Optional<User> user = userService.getById(userId);
        if (user.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            List<Order> orders = new ArrayList<Order>(user.get().getOrders());
            if (Objects.isNull(orders) || orders.isEmpty() || orders.size() < startPosition || startPosition < 0) {
                throw new NoResourceFoundException();
            } else {
                int ordersCount = orders.size();
                List<Order> sublist;
                if (ordersCount > startPosition + limit) {
                    sublist = orders.subList(startPosition, startPosition + limit);
                } else {
                    sublist = orders.subList(startPosition, ordersCount);
                }
                List<OrderDto> orderSublist = sublist.stream().map(order -> new OrderDto(order))
                        .collect(Collectors.toList());
                int sublistSize = orderSublist.size();
                for (int i = 0; i < sublistSize; i++) {
                    addCommonOrderHateoasLinks(orderSublist.get(i), startPosition + i);
                }

                ListDto<OrderDto> listDto = new ListDto<OrderDto>(orderSublist);
                listDto.addNextPageLink(methodOn(UserControllerImpl.class).getPage(limit, limit + startPosition));
                listDto.addPreviousPageLink(methodOn(UserControllerImpl.class).getPage(limit, startPosition - limit));
                return listDto;
            }

        }
    }

    @Override
    public OrderDto addOrder(long userId, long certificateId) {
        Optional<User> user = userService.getById(userId);
        Optional<Certificate> certificate = certificateService.getById(certificateId);
        if (user.isEmpty() || certificate.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            Order order = Order.builder().purchaseTime(Instant.now()).purchasePrice(certificate.get().getPrice())
                    .certificate(certificate.get()).user(user.get()).build();

            Order savedOrderOptional = orderService.save(order);

            Order savedOrder = savedOrderOptional;
            OrderDto dto = new OrderDto(savedOrder);
            addCommonOrderHateoasLinks(dto, 0);
            return dto;

        }
    }

    private void addCommonUserHateoasLinks(UserDto user, int limit) {
        user.add(linkTo(methodOn(UserControllerImpl.class).getById(user.getUserId())).withSelfRel());
        user.add(linkTo(methodOn(UserControllerImpl.class).getUsersOrders(user.getUserId(), limit, DEFAULT_START_ID))
                .withRel(ORDER_RELATION));
    }

    private void addCommonOrderHateoasLinks(OrderDto order, int orderPosition) {
        order.add(linkTo(methodOn(UserControllerImpl.class).getOrder(order.getUser().getUserId(), orderPosition))
                .withSelfRel());
    }
}