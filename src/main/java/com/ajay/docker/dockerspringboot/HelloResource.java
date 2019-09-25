package com.ajay.docker.dockerspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/docker/hello")
public class HelloResource {

    @GetMapping
    public String hello(){
        System.out.println("just saying hi!");
        return "Hi All, Welcome to the DevOps Pipeline Demo";
    }
}
