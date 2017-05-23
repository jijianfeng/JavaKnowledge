package com.jjf.mybatis;

import com.jjf.mybatis.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * Created by jjf_lenovo on 2017/5/19.
         */
public class MybatisTest extends BaseSpringTest {
    @Autowired
    private UserMapper mapper;

    @Test
    public void select(){
        HashMap map = mapper.selectById(1);
        System.out.println(map.toString());
    }
}
