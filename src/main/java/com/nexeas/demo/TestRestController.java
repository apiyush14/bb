package com.nexeas.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

	@GetMapping(path="/helloworld")
	public String testMethod()
	{
		return "Hello World From One Health!!!";
	}
}
