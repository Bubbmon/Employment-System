package com.system.util;

import com.system.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Legion
 * @Date 2021/6/14 15:50
 * @Description
 */
@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
public class TokenUtilTest {
    @Autowired
    private TokenUtil tokenUtil;

    @Test
    public void test(){
        String token = tokenUtil.generateToken(true, "abcde");
        tokenUtil.save(token, "abcde");
        String check = tokenUtil.check(token);
        System.out.println(check);

    }

}
