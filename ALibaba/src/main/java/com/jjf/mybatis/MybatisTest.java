package com.jjf.mybatis;

import com.jjf.mybatis.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jjf_lenovo on 2017/5/19.
 */
public class MybatisTest extends BaseSpringTest {
    @Autowired
    private UserMapper mapper;

    @Test
    public void Crud(){
//        User user = new User();
//        user.setAge(21);
//        user.setUserName("user");
//        mapper.insert(user);
//        Assert.assertTrue(user.getId()!=null);
        mapper.deleteByPrimaryKey(1);
    }
}
