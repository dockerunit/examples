package com.github.dockerunit.examples.springboot;

import static com.github.dockerunit.examples.springboot.Constants.BAR_VALUE_FROM_DESCRIPTOR;
import static com.github.dockerunit.examples.springboot.Constants.FOO_VALUE_FROM_IMAGE;
import static com.github.dockerunit.examples.springboot.Constants.SERVICE_NAME;
import static org.hamcrest.Matchers.equalTo;

import com.github.dockerunit.annotation.WithSvc;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import com.github.dockerunit.DockerUnitRunner;
import com.github.dockerunit.Service;
import com.github.dockerunit.ServiceContext;
import com.github.dockerunit.ServiceInstance;
import com.github.dockerunit.examples.springboot.descriptors.BaseDescriptor;
import com.github.dockerunit.examples.springboot.descriptors.TestConfigVolumeDescriptor;
import com.github.dockerunit.examples.springboot.descriptors.TestEnvDescriptor;

import io.restassured.RestAssured;

@RunWith(DockerUnitRunner.class)
@Category(ContainerTest.class)
public class RunnerBasedTest {

	@Test
	@WithSvc(svc = BaseDescriptor.class)
	public void healthCheckShouldReturn200(ServiceContext context) {
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
	@WithSvc(svc = BaseDescriptor.class, replicas= 2)
	public void healthCheckShouldReturn200FromEachReplica(ServiceContext context) {
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
	@WithSvc(svc = BaseDescriptor.class)
	public void greetingShouldReturnImageConfigValue(ServiceContext context) {
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
	
	@Test
	@WithSvc(svc = TestConfigVolumeDescriptor.class)
	public void greetingShouldReturnTestConfigValue(ServiceContext context) {
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
				.body("greeting", equalTo("Hello Dockerunit!!!"));
	}
	
	@Test
	@WithSvc(svc = BaseDescriptor.class)
	public void envShouldReturnValuesFromImage(ServiceContext context) {
		Service s = context.getService(SERVICE_NAME);
		ServiceInstance si = s.getInstances().stream().findAny().get();
		
		RestAssured
			.given()
				.baseUri("http://" + si.getIp() + ":" + si.getPort())
			.when()
				.get("/env/foo")
			.then()
				.assertThat()
				.statusCode(200)
				.and()
				.body("value", equalTo(FOO_VALUE_FROM_IMAGE));
	}
	
	@Test
	@WithSvc(svc = TestEnvDescriptor.class)
	public void envShouldReturnValuesFromDescriptor(ServiceContext context) {
		Service s = context.getService(SERVICE_NAME);
		ServiceInstance si = s.getInstances().stream().findAny().get();
		
		RestAssured
			.given()
				.baseUri("http://" + si.getIp() + ":" + si.getPort())
			.when()
				.get("/env/bar")
			.then()
				.assertThat()
				.statusCode(200)
				.and()
				.body("value", equalTo(BAR_VALUE_FROM_DESCRIPTOR));
	}
	
}
