package com.example.backendproject.purewebsocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
public class HtmlController {
    //@GetMapping("/")
    public String index() {
        //return "redirect:/purechat2.html";
        return "redirect:/stompchat2.html";
    }
}