package com.rest.webservices.restful_web_services.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public HelloBean getHello() {
        return new HelloBean("Hello v2...");
    }
}
