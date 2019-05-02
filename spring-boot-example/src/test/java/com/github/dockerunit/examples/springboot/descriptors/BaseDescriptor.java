package com.github.dockerunit.examples.springboot.descriptors;

import com.github.dockerunit.annotation.PublishPorts;
import com.github.dockerunit.annotation.Svc;
import com.github.dockerunit.discovery.consul.annotation.UseConsulDns;
import com.github.dockerunit.discovery.consul.annotation.WebHealthCheck;
import com.github.dockerunit.examples.springboot.Constants;

@Svc(name = Constants.SERVICE_NAME, image = Constants.IMAGE_NAME)
@WebHealthCheck(port = 8080)
@PublishPorts
@UseConsulDns
public class BaseDescriptor {
	
}
