package com.bootvue.core.ddo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDo {
    private Long id;
    private String key;
    private String icon;
    private String title;
    private Integer sort;
    private Long pId;
    private Boolean defaultSelect;
    private Boolean defaultOpen;
    private String actionIds;
}
