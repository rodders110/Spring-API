package com.daly_roddy.API.Development;

import com.daly_roddy.API.Development.Controller.LicenseKeyController;
import com.daly_roddy.API.Development.Details.DecryptionDetails;
import com.daly_roddy.API.Development.Details.EncryptionDetails;
import com.daly_roddy.API.Development.Services.AuthenticationService;
import com.daly_roddy.API.Development.Services.EncryptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiDevelopmentApplicationTests {

	private MockMvc mockMvc;

	private EncryptionDetails authorisedEncryptionDetails;
	private EncryptionDetails unauthorisedEncryptionDetails;
	private DecryptionDetails authorisedDecryptionDetails;
	private DecryptionDetails badDetailsDecryptionDetails;
	private DecryptionDetails badKeyDecryptionDetails;

	@Spy
	private AuthenticationService authenticationService;

	@Spy
	private EncryptionService encryptionService;

	@InjectMocks
	private LicenseKeyController licenseKeyController = new LicenseKeyController();

	@Before
	public void setUpRequestObjects(){

		mockMvc = MockMvcBuilders
				.standaloneSetup(licenseKeyController)
				.build();

		authorisedEncryptionDetails = new EncryptionDetails();
		authorisedEncryptionDetails.setFullName("James Bond");
		authorisedEncryptionDetails.setProgram("Claris works");
		authorisedEncryptionDetails.setPassword("password1");

		unauthorisedEncryptionDetails = new EncryptionDetails();
		unauthorisedEncryptionDetails.setFullName("James Bond");
		unauthorisedEncryptionDetails.setProgram("Claris works");
		unauthorisedEncryptionDetails.setPassword("tiddlewinks");

		authorisedDecryptionDetails = new DecryptionDetails();
		authorisedDecryptionDetails.setFullName("James Bond");
		authorisedDecryptionDetails.setProgram("Claris Works");
		authorisedDecryptionDetails.setLicense("f577e762a302036e92f1f882a970d70375a472fe21b2ec809f01e25cd67d4db4");

		badDetailsDecryptionDetails = new DecryptionDetails();
		badDetailsDecryptionDetails.setFullName("John Wand");
		badDetailsDecryptionDetails.setProgram("Claris Works");
		badDetailsDecryptionDetails.setLicense("f577e762a302036e92f1f882a970d70375a472fe21b2ec809f01e25cd67d4db4");

		badKeyDecryptionDetails = new DecryptionDetails();
		badKeyDecryptionDetails.setFullName("James Bond");
		badKeyDecryptionDetails.setProgram("Claris Works");
		badKeyDecryptionDetails.setLicense("f577e762a302036g92f1f882a970d70375b472fe21b2ec809f01e25cd67d4db5");
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void givenAValidPassword()throws Exception{

		URI uri = new URI("/getkey");
		MvcResult result = mockMvc.perform(post(uri)
				.content(asJsonString(authorisedEncryptionDetails))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();

		Assert.assertEquals("a9a9e66e807fd4f4743dfdfdb324cbd96b4ae78282f832ecdbaaf3f3d7421e73", content);
	}

	@Test
	public void givenAnInvalidPassword() throws Exception{

		URI uri = new URI("/getkey");
		MvcResult result = mockMvc.perform(post(uri)
				.content(asJsonString(unauthorisedEncryptionDetails))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isUnauthorized()).andReturn();

		String content = result.getResponse().getContentAsString();

		Assert.assertEquals("Password incorrect", content);
	}

	@Test
	public void givenAValidKey() throws Exception{

		URI uri = new URI("/verifyKey");
		MvcResult result = mockMvc.perform(post(uri)
				.content(asJsonString(authorisedDecryptionDetails))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().is(204)).andReturn();

		String content = result.getResponse().getContentAsString();

		Assert.assertEquals("", content);
	}

	@Test
	public void givenAValidKeyButWrongDetails() throws Exception{

		URI uri = new URI("/verifyKey");
		MvcResult result = mockMvc.perform(post(uri)
				.content(asJsonString(badDetailsDecryptionDetails))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isNotFound()).andReturn();

		String content = result.getResponse().getContentAsString();

		Assert.assertEquals("", content);
	}

	@Test
	public void givenAnInvalidKey() throws Exception{

		URI uri = new URI("/verifyKey");
		MvcResult result = mockMvc.perform(post(uri)
				.content(asJsonString(badKeyDecryptionDetails))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().is(404)).andReturn();

		String content = result.getResponse().getContentAsString();

		Assert.assertEquals("", content);
	}

	public static String asJsonString(final Object obj){
		try{
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}
