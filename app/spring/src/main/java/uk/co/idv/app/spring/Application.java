package uk.co.idv.app.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        /*ConfigurableApplicationContext context = */SpringApplication.run(Application.class, args);
        //SquigglyConfig.init(context);
    }

}
