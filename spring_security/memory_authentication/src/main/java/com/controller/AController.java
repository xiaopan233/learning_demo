package com.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/anon")
    public String anon(){
        return "anon";
    }

    @GetMapping("/v2/test1")
    public String login(String userNa, String psd){
        return "/v2/test1";
    }
}
