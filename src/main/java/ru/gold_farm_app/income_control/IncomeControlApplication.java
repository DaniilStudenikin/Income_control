package ru.gold_farm_app.income_control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IncomeControlApplication {


    public static void main(String[] args) {
        SpringApplication.run(IncomeControlApplication.class, args);
    }

}
