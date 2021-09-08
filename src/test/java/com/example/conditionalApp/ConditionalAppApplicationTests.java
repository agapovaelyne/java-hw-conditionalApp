package com.example.conditionalApp;

import com.example.conditionalApp.profile.DevProfile;
import com.example.conditionalApp.profile.ProductionProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ConditionalAppApplicationTests {

	private final static int DEV_APP_PORT = 8080;
	private final static int PROD_APP_PORT = 8081;

	@Autowired
	TestRestTemplate restTemplate;

	@Container
	private static GenericContainer<?> devAppContainer = new GenericContainer<>("dev-app")
			.withExposedPorts(DEV_APP_PORT);

	@Container
	private static GenericContainer<?> prodAppContainer = new GenericContainer<>("prod-app")
			.withExposedPorts(PROD_APP_PORT);

	@Test
	void devAppResponse_test() {
		int devAppPort = devAppContainer.getMappedPort(DEV_APP_PORT);
		ResponseEntity<String> devAppResponseEntity = restTemplate.getForEntity("http://localhost:" + devAppPort + "/profile", String.class);
		assertEquals(new DevProfile().getProfile(),devAppResponseEntity.getBody());
	}

	@Test
	void prodAppResponse_test() {
		int prodAppPort = prodAppContainer.getMappedPort(PROD_APP_PORT);
		ResponseEntity<String> prodAppResponseEntity = restTemplate.getForEntity("http://localhost:" + prodAppPort + "/profile", String.class);
		assertEquals(new ProductionProfile().getProfile(), prodAppResponseEntity.getBody());
	}

}
