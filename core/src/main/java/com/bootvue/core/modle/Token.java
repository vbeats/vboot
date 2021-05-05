package com.bootvue.core.modle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {
    private static final long serialVersionUID = -8789855984567396700L;

    private Long userId;
    private String username;
    private String tenantId;
    private String type;  // token类型   access_token | refresh_token
}
