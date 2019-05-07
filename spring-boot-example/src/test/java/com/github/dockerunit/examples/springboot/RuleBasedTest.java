
package com.github.dockerunit.examples.springboot;

import com.github.dockerunit.core.Service;
import com.github.dockerunit.core.ServiceContext;
import com.github.dockerunit.core.ServiceInstance;
import com.github.dockerunit.core.annotation.WithSvc;
import com.github.dockerunit.examples.springboot.descriptors.BaseDescriptor;
import com.github.dockerunit.junit4.DockerUnitRule;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.github.dockerunit.examples.springboot.Constants.SERVICE_NAME;
import static org.hamcrest.Matchers.equalTo;

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
