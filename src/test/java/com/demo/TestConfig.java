package com.demo;

import org.apache.cxf.binding.BindingConfiguration;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.openhealthtools.ihe.atna.auditor.context.AuditorModuleConfig;
import org.openhealthtools.ihe.atna.auditor.context.AuditorModuleContext;
import org.openhealthtools.ihe.atna.auditor.queue.SynchronousAuditQueue;
import org.openhealthtools.ihe.atna.auditor.sender.AuditStringSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

import java.util.Collections;

/**
 * Created by remcoo on 20/02/2017.
 */
@Configuration
public class TestConfig {

	@Autowired
	private SpringBus springBus;

	@Autowired
	private BindingConfiguration bindingConfiguration;


	@Bean
	Endpoint mockQuery(RegistryMock registryMock) {
		EndpointImpl endpoint = new EndpointImpl(springBus, registryMock);

		endpoint.setFeatures(Collections.singletonList(new WSAddressingFeature()));
		endpoint.setBindingConfig(bindingConfiguration);
		endpoint.publish("/registry");

		return endpoint;
	}

	@Bean
	AuditorModuleContext auditorModuleContext() throws IllegalAccessException, InstantiationException {
		AuditorModuleContext context = AuditorModuleContext.getContext();

		context.setSender(new AuditStringSenderImpl());
		context.setQueue(new SynchronousAuditQueue());

		AuditorModuleConfig config = new AuditorModuleConfig();
		config.setAuditorEnabled(true);
		config.setAuditRepositoryHost("localhost");
		config.setAuditRepositoryPort(6514);

		return context;
	}

}
