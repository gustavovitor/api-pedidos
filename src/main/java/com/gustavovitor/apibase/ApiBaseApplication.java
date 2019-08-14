package com.gustavovitor.apibase;

import com.gustavovitor.apibase.config.properties.APIProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(APIProperty.class)
public class ApiBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiBaseApplication.class, args);
    }

}

