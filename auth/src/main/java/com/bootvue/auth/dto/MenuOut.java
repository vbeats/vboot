package com.bootvue.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单权限")
public class MenuOut {
    @ApiModelProperty(notes = "菜单key", required = true)
    private String key;

    @ApiModelProperty(notes = "菜单icon")
    private String icon;

    @ApiModelProperty(notes = "菜单名称")
    private String title;

    @ApiModelProperty(notes = "是否默认选择")
    private Boolean defaultSelect;

    @ApiModelProperty(notes = "二级菜单是否默认展开")
    private Boolean defaultOpen;

    @ApiModelProperty(notes = "权限表达式")
    private Set<String> permissions;

    @ApiModelProperty(notes = "子菜单")
    private List<MenuOut> children;
}
