package com.epam.esm.zotov.mjcschool.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Role;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class UserDto extends RepresentationModel<UserDto> {
    @Min(value = 1)
    private Long userId;
    @NotBlank
    private String username;
    @NotBlank
    private String role;

    public User convertToUser() {
        return new User(userId, username, null, new Role(null, role));
    }

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.role = user.getRole().getName();
    }
}
