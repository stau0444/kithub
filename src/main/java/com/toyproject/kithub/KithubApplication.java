package com.toyproject.kithub;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KithubApplication {

    public static void main(String[] args) {
        SpringApplication.run(KithubApplication.class, args);
    }


    //lazy 일 경우에 무시하게 해준다.
    //옵션으로 강제 lazy 로딩 시킬 수도 있다 .
    @Bean
    Hibernate5Module hibernate5Module(){
        return new Hibernate5Module();
    }
}
