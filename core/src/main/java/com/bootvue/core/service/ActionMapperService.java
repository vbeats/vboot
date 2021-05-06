package com.bootvue.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootvue.core.entity.Action;
import com.bootvue.core.mapper.ActionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActionMapperService {
    private final ActionMapper actionMapper;

    public List<Action> getActions(Set<Long> ids) {
        return actionMapper.selectList(
                new QueryWrapper<Action>().lambda()
                        .in(Action::getId, ids)
        );
    }

    public Action getAction(String api) {
        return actionMapper.selectOne(new QueryWrapper<Action>()
                .lambda().eq(Action::getApi, api)
        );
    }
}
