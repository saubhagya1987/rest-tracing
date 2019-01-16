package com.javainuse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
class ELKController {
	private static final Logger LOG = Logger.getLogger(ELKController.class.getName());

	/*@Autowired
	RestTemplate restTemplete;*/

	/*@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}*/

	@RequestMapping(value = "/elk")
	public String helloWorld() {
		String response = "Welcome to javainuse" + new Date();
		LOG.log(Level.INFO, response);

		return response;
	}

	@RequestMapping(value = "/exception")
	public String exception() {
		String response = "";
		try {
			throw new Exception("Opps Exception raised....");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();
			LOG.error("Exception - " + stackTrace);
			response = stackTrace;
		}

		return response;
	}
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/start-error-chaining")
	public String hello() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/exception", String.class);
		return "->Chaining + " + response.getBody();
	}

	
	
}
