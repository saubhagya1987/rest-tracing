/***************************************************************************
 *  Copyright (C) Proximus 2018
 *
 *  The reproduction, transmission or use of this document  or its contents
 *  is not  permitted without  prior express written consent of Proximus.
 *  Offenders will be liable for damages. All rights, including  but not 
 *  limited to rights created by patent grant or registration of a utility
 *  model or design, are reserved.
 *
 *  Proximus reserves the right to modify technical specifications and features.
 *
 *  Technical specifications and features are binding only in so far as they
 *  are specifically and expressly agreed upon in a written contract.
 *
 **************************************************************************/

package com.javainuse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.uber.jaeger.Configuration;
import com.uber.jaeger.samplers.ProbabilisticSampler;

/**
 * OpenTracing.java
 *
 * @author Narendra.Kumar
 *
 * @date Dec 26, 2018
 *
 */
@org.springframework.context.annotation.Configuration
public class OpenTracingConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Bean("jaegerTracer")
	public io.opentracing.Tracer jaegerTracer() {
		return new Configuration("spring-boot-elk",
				new Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE, 1,
						env.getProperty("jaeger.host")
								+ Integer.parseInt(env.getProperty("jaeger.sampler.manager.host.port"))),
				new Configuration.ReporterConfiguration(false, env.getProperty("jaeger.host"),
						Integer.parseInt(env.getProperty("jaeger.agent.port")), 1000, 100)).getTracer();
	}
}
