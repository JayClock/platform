package autodev.ddd.platform.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"autodev.ddd.platform.mybatis", "autodev.ddd.mybatis.support"})
public class MyBatis {
}
