package com.controller;


import com.Service.UserSerivce;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {
    @Autowired
    private UserSerivce userSerivce;

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

    @GetMapping("/testDataSource")
    public String testDataSource(){
        User ppp = userSerivce.getUserByUsername("ppp");
        System.out.println(ppp);
        return ppp.getRole();
    }
}
