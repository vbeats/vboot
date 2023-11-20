package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.datasource.config.JsonArrayTypeHandler;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "`client`", autoResultMap = true)
public class Client implements Serializable {

    @TableId
    private Long id;

    private String clientId;

    private String secret;

    @TableField(value = "grant_type", typeHandler = JsonArrayTypeHandler.class)
    private List<String> grantType;

    private String deviceType;

    private Long activeTimeout;

    private Long timeout;

    @TableLogic
    private Boolean delFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
