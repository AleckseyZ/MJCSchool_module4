package com.epam.esm.zotov.mjcschool.api.controller.user;

import java.security.Principal;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.epam.esm.zotov.mjcschool.api.controller.ReadController;
import com.epam.esm.zotov.mjcschool.api.dto.ListDto;
import com.epam.esm.zotov.mjcschool.api.dto.OrderDto;
import com.epam.esm.zotov.mjcschool.api.dto.UserDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserController extends ReadController<UserDto> {
        @GetMapping("/{userId}/orders")
        public ListDto<OrderDto> getUsersOrders(@PathVariable @Min(1) long userId,
                        @RequestParam @Min(0) int startPosition, @RequestParam @Min(1) int limit, Principal principal);

        @GetMapping("/{userId}/orders/{orderPosition}")
        public OrderDto getOrder(@PathVariable @Min(1) long userId, @Min(0) @PathVariable int orderPosition,
                        Principal principal);

        @PostMapping("/{userId}/orders")
        @ResponseStatus(HttpStatus.CREATED)
        public OrderDto addOrder(@PathVariable @Min(1) long userId, @RequestParam @Min(1) long certificateId,
                        Principal principal);

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public UserDto signUp(@RequestBody @NotEmpty Map<String, String> credentials);
}