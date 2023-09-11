package com.codestepfish.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "认证响应")
public class AuthResponse {

    @Schema(description = "access_token")
    private String accessToken;

    @Schema(description = "access_token有效时长 s")
    private Long expire;
}
