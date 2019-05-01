package com.github.dockerunit.examples.springboot.descriptors;

import com.github.dockerunit.annotation.PublishPort;
import com.github.dockerunit.annotation.Svc;
import com.github.dockerunit.annotation.Volume;
import com.github.dockerunit.discovery.consul.annotation.WebHealthCheck;
import com.github.dockerunit.examples.springboot.Constants;

@Svc(name = Constants.SERVICE_NAME, image = Constants.IMAGE_NAME)
@WebHealthCheck(port =8080)
@PublishPort(container =8080, host =8080)
@Volume(host="test.properties", container="/application.properties", useClasspath=true)
public class TestConfigVolumeDescriptor {

}
