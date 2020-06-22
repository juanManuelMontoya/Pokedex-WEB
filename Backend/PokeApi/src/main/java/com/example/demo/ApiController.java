package com.example.demo;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiController {


	@Autowired
	private RestTemplate restTemplate;
	
	private static String url = "https://pokeapi.co/api/v2/pokemon/{id}";
	private static String urlRegion = "https://pokeapi.co/api/v2/region/{id}";

	 @CrossOrigin(origins = "*", allowedHeaders = "*")
	 @RequestMapping(value = "/pokemon/{id}")
	   public String getPokemonList(@PathVariable("id") int id) {
		 
		  HttpHeaders headers = createHeader();
		  HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, id).getBody();
	      
	   }
	 
	 @CrossOrigin(origins = "*", allowedHeaders = "*")
	 @RequestMapping(value = "/region/{id}")
	 public String getRegionList(@PathVariable("id") int id) {
		 
		  HttpHeaders headers = createHeader();
		  HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange(urlRegion, HttpMethod.GET, entity, String.class, id).getBody();
	      
	   }
	 
	 
	 private HttpHeaders createHeader() {
		  HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
	      
	      return headers;
	 }
}
