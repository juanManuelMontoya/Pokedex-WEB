package com.example.demo.controllers;

import java.util.List;
import java.util.Arrays;

import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/pokeapi")
public class DepartmentController {

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/pokemons")
	public List<Object> getPokemons(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", ",Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		String url = "https://pokeapi.co/api/v2/pokemon/";
		//Object[] objects = restTemplate.getForObject(url, Object[].class);
		ResponseEntity<Object[]> objects = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
				
		return Arrays.asList(objects);
	}
	
	@GetMapping("/regions")
	public List<Object> getRegions(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", ",Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		String url = "https://pokeapi.co/api/v2/region/";
		//Object[] objects = restTemplate.getForObject(url, Object[].class);
		ResponseEntity<Object[]> objects = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
				
		return Arrays.asList(objects);
	}
}
