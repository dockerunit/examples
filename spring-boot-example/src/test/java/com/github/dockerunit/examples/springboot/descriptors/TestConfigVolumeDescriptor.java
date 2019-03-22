package com.github.dockerunit.examples.springboot.descriptors;

import com.github.dockerunit.annotation.Image;
import com.github.dockerunit.annotation.Named;
import com.github.dockerunit.annotation.PortBinding;
import com.github.dockerunit.annotation.Volume;
import com.github.dockerunit.discovery.consul.annotation.WebHealthCheck;
import com.github.dockerunit.examples.springboot.Constants;

@Named(Constants.SERVICE_NAME)
@Image(Constants.IMAGE_NAME)
@WebHealthCheck(exposedPort=8080)
@PortBinding(exposedPort=8080, hostPort=8080)
@Volume(host="test.properties", container="/application.properties", useClasspath=true)
public class TestConfigVolumeDescriptor {

}
