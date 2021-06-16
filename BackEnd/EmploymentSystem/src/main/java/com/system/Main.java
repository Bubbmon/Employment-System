package com.system;

import com.system.config.WebSocketConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

/**
 * @Author Legion
 * @Date 2021/6/12 14:41
 * @Description
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.system.mapper"})
@Import(WebSocketConfiguration.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
