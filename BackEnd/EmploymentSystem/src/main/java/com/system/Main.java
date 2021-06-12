package com.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author Legion
 * @Date 2021/6/12 14:41
 * @Description
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.system.mapper"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
