package com.github.dockerunit.examples.springboot.descriptors;

import com.github.dockerunit.annotation.Env;
import com.github.dockerunit.annotation.PublishPort;
import com.github.dockerunit.annotation.Svc;
import com.github.dockerunit.discovery.consul.annotation.WebHealthCheck;
import com.github.dockerunit.examples.springboot.Constants;

import static com.github.dockerunit.examples.springboot.Constants.BAR_VALUE_FROM_DESCRIPTOR;
import static com.github.dockerunit.examples.springboot.Constants.FOO_VALUE_FROM_DESCRIPTOR;

@Svc(name = Constants.SERVICE_NAME, image = Constants.IMAGE_NAME)
@WebHealthCheck(port = 8080)
@PublishPort(container = 8080, host = 8080)
@Env({"FOO=" + FOO_VALUE_FROM_DESCRIPTOR, "BAR=" + BAR_VALUE_FROM_DESCRIPTOR})
public class TestEnvDescriptor {

}
