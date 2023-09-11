package com.codestepfish;

import com.codestepfish.datasource.service.DeptMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppTest {

    private final DeptMapperService deptMapperService;

    @Test
    void test() {
        List<Long> ids = deptMapperService.findDeptIdsByPid(1L);
        log.info("=============> {}", ids);
    }
}
