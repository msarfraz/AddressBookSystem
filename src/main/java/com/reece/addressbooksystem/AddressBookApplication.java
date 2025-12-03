package com.reece.addressbooksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AddressBookApplication {

    @RequestMapping("/")
    public String home() {
        return "<html>Hello World<html>";
    }

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }

}
