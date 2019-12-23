package com.eki.shipment.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.shipment.run.MysqlBootApplication;

/**
 * TestRestTemplate is only auto-configured when @SpringBootTest has been
 * configured with a webEnvironment that means it starts the web container and
 * listens for HTTP requests. For example:
 * 
 * @SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT) @author eckha
 *
 */
@SpringBootTest(classes = MysqlBootApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")

public class AbstractWebControllerTest {
	@Test
	public void testDummy() {
		
	}
}
