package com.fitlers.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticDetailsService {

@GetMapping(path = "/home")
public String getHomePage() {
	return "index.html";
}

}
