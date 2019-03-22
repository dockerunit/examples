package com.github.dockerunit.examples.springboot.descriptors;

import com.github.dockerunit.examples.springboot.Constants;
import com.github.dockerunit.annotation.Image;
import com.github.dockerunit.annotation.Named;
import com.github.dockerunit.annotation.PublishPorts;
import com.github.dockerunit.discovery.consul.annotation.WebHealthCheck;

@Named(Constants.SERVICE_NAME)
@Image(Constants.IMAGE_NAME)
@WebHealthCheck(exposedPort=8080)
@PublishPorts
public class BaseDescriptor {
	
}
