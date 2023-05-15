package edu.aschwartz.demo;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;

	@BeforeEach
	public void setup(){
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	void appelUrlRacine_OKAttendu() throws Exception{
		mvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	void appelUrlRacine_MessageHello() throws Exception{
		mvc.perform(get("/")).andExpect(content().string("Coucou les patates + le serveur marche mais y a rien ici"));
	}

	@Test
	void utilisateurNonConnecteAppelUrlListeUtilisateur_403attendu() throws Exception{
		mvc.perform(get("/utilisateurs")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = {"UTILISATEUR"})
	void utilisateurNonConnecteAppelUrlListeUtilisateur_OKAttendu() throws Exception{
		mvc.perform(get("/utilisateurs")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = {"ADMINISTRATEUR"})
	void administrateurNonConnecteAppelUrlListeUtilisateur_OKAttendu() throws Exception{
		mvc.perform(get("/utilisateurs")).andExpect(status().isOk());
	}
}
