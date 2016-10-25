package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        Engine e = new Engine();
        SpringApplication.run(Application.class, args);
    }
    
}
