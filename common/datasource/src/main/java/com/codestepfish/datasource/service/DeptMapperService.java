package com.codestepfish.datasource.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.datasource.entity.Dept;
import com.codestepfish.datasource.mapper.DeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeptMapperService extends ServiceImpl<DeptMapper, Dept> implements IService<Dept> {

    public List<Long> findDeptIdsByPid(Long pid) {
        return list(Wrappers.<Dept>lambdaQuery()
                .apply("json_contains(pids,json_array({0}))", pid)
        ).stream().map(Dept::getId).collect(Collectors.toList());
    }
}
