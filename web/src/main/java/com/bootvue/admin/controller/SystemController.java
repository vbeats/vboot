package com.bootvue.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin")
@Api(tags = "系统设置")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemController {

    @GetMapping("/userinfo")
    @ApiOperation("获取用户/菜单/权限等信息")
    public void test() {
        log.info("test....");
    }
}
