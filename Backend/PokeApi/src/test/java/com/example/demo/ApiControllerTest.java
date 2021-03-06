package com.example.demo;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = ApiController.class)
public class ApiControllerTest {
	
	@Autowired(required = true)
	private ApiController apiController;
	
	@Autowired(required = true)
	private RestTemplate restTemplate;
		
	private static String url = "https://pokeapi.co/api/v2/pokemon/{id}";
	private static String urlRegion = "https://pokeapi.co/api/v2/region/{id}";

	private MockRestServiceServer mockServer; 
	
	@Before
	public void setUp() {
		this.mockServer = MockRestServiceServer.createServer(restTemplate);
	}
	
	@Test
	public void getPokemonListOk() {
		int pokemonId = 1;
		
		UriTemplateHandler uriTemplate = new DefaultUriTemplateHandler();
		String uriExpanded = uriTemplate.expand(url, pokemonId).toString();
		
		mockServer.expect(requestTo(uriExpanded))
						 .andExpect(method(HttpMethod.GET))
						 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
						 .andRespond(withStatus(HttpStatus.OK)
								 .contentType(MediaType.APPLICATION_JSON));
		
		apiController.getPokemonList(pokemonId);
		
		mockServer.verify();
	}
	
	@Test
	public void getRegionListOk() {
		int regionId = 1;
		
		UriTemplateHandler uriTemplate = new DefaultUriTemplateHandler();
		String uriExpanded = uriTemplate.expand(urlRegion, regionId).toString();
		
		mockServer.expect(requestTo(uriExpanded))
						 .andExpect(method(HttpMethod.GET))
						 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
						 .andRespond(withStatus(HttpStatus.OK)
								 .contentType(MediaType.APPLICATION_JSON));
		
		apiController.getPokemonList(regionId);
		
		mockServer.verify();
	}
	
}
