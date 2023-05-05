package edu.aschwartz.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello(){
        return "<h1>Coucou les patates + le serveur marche mais y a rien ici</h1>";
    }
}
