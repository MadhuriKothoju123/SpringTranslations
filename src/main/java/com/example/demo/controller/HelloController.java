package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	
	
	  @GetMapping("/")
	  public String hello() {
	      return "hello";
	  }
	  
	  @GetMapping("/greet")
	  public String hellogreet() {
	      return "<div>hello</div>";
	  }
	  
	  
	 
	}