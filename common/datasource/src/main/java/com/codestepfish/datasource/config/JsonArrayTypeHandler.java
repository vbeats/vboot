package com.codestepfish.datasource.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.Assert;

@Slf4j
@MappedTypes({JSONArray.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonArrayTypeHandler extends AbstractJsonTypeHandler<JSONArray> {
    private final Class<?> type;

    public JsonArrayTypeHandler(Class<?> type) {
        Assert.notNull(type, "Type argument cannot be null");
        this.type = type;
    }


    @Override
    protected JSONArray parse(String json) {
        return JSON.parseArray(json);
    }

    @Override
    protected String toJson(JSONArray obj) {
        return JSON.toJSONString(obj, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteNullStringAsEmpty);
    }
}
