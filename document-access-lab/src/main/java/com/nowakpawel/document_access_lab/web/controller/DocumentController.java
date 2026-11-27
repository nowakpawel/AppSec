package com.nowakpawel.document_access_lab.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {
    @GetMapping("/")
    public void hello() {
        System.out.println("Hello from controller");
    }
}
