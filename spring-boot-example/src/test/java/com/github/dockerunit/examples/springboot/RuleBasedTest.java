
package com.github.dockerunit.examples.springboot;

import static com.github.dockerunit.examples.springboot.Constants.SERVICE_NAME;
import static org.hamcrest.Matchers.equalTo;

import com.github.dockerunit.annotation.WithSvc;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.github.dockerunit.DockerUnitRule;
import com.github.dockerunit.Service;
import com.github.dockerunit.ServiceContext;
import com.github.dockerunit.ServiceInstance;
import com.github.dockerunit.examples.springboot.descriptors.BaseDescriptor;

import io.restassured.RestAssured;

@Category(ContainerTest.class)
@WithSvc(svc = BaseDescriptor.class, replicas = 2)
public class RuleBasedTest {
	
	@Rule
	public DockerUnitRule rule = new DockerUnitRule();
	
	private ServiceContext context;
	
	@Before
	public void setup() {
		context = DockerUnitRule.getDefaultServiceContext();
	}
	
	@Test
	public void healthCheckShouldReturn200() {
		Service s = context.getService(SERVICE_NAME);
		ServiceInstance si = s.getInstances().stream().findAny().get();
		
		RestAssured
			.given()
				.baseUri("http://" + si.getIp() + ":" + si.getPort())
			.when()
				.get("/health-check")
			.then()
				.assertThat()
				.statusCode(200);
	}
	
	@Test
	public void healthCheckShouldReturn200FromEachReplica() {
		Service s = context.getService(SERVICE_NAME);
		s.getInstances().forEach(si -> {
		RestAssured
			.given()
				.baseUri("http://" + si.getIp() + ":" + si.getPort())
			.when()
				.get("/health-check")
			.then()
				.assertThat()
				.statusCode(200);
		});
	}
	
	@Test
	public void greetingShouldReturnImageConfigValue() {
		Service s = context.getService(SERVICE_NAME);
		ServiceInstance si = s.getInstances().stream().findAny().get();
		
		RestAssured
			.given()
				.baseUri("http://" + si.getIp() + ":" + si.getPort())
			.when()
				.get("/greeting")
			.then()
				.assertThat()
				.statusCode(200)
				.and()
				.body("greeting", equalTo("Hello world!"));
	}
	
}
