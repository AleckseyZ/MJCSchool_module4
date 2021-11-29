package com.epam.esm.zotov.mjcschool.api.controller.user;

import javax.validation.constraints.Min;

import com.epam.esm.zotov.mjcschool.api.controller.ReadController;
import com.epam.esm.zotov.mjcschool.api.dto.ListDto;
import com.epam.esm.zotov.mjcschool.api.dto.OrderDto;
import com.epam.esm.zotov.mjcschool.api.dto.UserDto;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserController extends ReadController<UserDto> {
    @PreAuthorize(value = "hasAuthority('admin') or #userId == authentication.principal.userId")
    @GetMapping("/{userId}/orders")
    public ListDto<OrderDto> getUsersOrders(@PathVariable @Min(1) long userId, @RequestParam @Min(1) int limit,
            @RequestParam @Min(0) int startPosition);

    @PreAuthorize(value = "hasAuthority('admin') or #userId == authentication.principal.userId")
    @GetMapping("/{userId}/orders/{orderPosition}")
    public OrderDto getOrder(@PathVariable @Min(1) long userId, @Min(0) @PathVariable int orderPosition);

    @PreAuthorize(value = "hasAuthority('admin') or #userId == authentication.principal.userId")
    @PostMapping("/{userId}/orders")
    public OrderDto addOrder(@PathVariable @Min(1) long userId, @RequestParam @Min(1) long certificateId);
}