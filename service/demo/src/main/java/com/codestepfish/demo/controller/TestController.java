package com.codestepfish.demo.controller;

import com.baomidou.lock.annotation.Lock4j;
import com.codestepfish.core.model.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestController {

    @PostMapping("/t1")
    @Lock4j(keys = "#name", expire = 10000, acquireTimeout = 2000)
    public R<String> test(String name) {
        return R.success("success");
    }
}
