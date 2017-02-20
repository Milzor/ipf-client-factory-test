package com.demo;

import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapBindingConfiguration;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Servlet;

@SpringBootApplication
public class IpfClientFactoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpfClientFactoryApplication.class, args);
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	SpringBus springBus() {
		return new SpringBus();
	}

	@Bean
	SoapBindingConfiguration bindingConfiguration() {
		SoapBindingConfiguration soapBindingConfiguration = new SoapBindingConfiguration();
		soapBindingConfiguration.setVersion(Soap12.getInstance());
		return soapBindingConfiguration;
	}

	@Bean
	ServletRegistrationBean cxfServlet() {
		Servlet cxfs = new CXFServlet();

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(cxfs);
		servletRegistrationBean.addUrlMappings("/services/*");

		return servletRegistrationBean;
	}

}
