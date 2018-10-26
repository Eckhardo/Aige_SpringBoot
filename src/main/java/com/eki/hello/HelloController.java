package com.eki.hello;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders="*")
@RestController
public class HelloController {
	@RequestMapping("/")
	public String home() {
		return "Hello AIGE!";
	}

}
