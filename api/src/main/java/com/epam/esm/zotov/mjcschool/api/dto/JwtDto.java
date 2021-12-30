package com.epam.esm.zotov.mjcschool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtDto {
    private String token;
    private String refreshToken;   
}