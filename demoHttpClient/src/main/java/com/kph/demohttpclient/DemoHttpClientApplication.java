package com.kph.demohttpclient;

import com.kph.demohttpclient.services.SimpleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoHttpClientApplication {

	@Autowired
	private SimpleClient demoPost;

	public static void main(String[] args) {
		SpringApplication.run(DemoHttpClientApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
//			System.out.println("inspect beans in this ctx");
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName: beanNames) {
//				System.out.println(beanName);
//			}
			System.out.println("Call demoRequest get method");
			demoPost.demoRequestGet();

			System.out.println("Call demoRequest post method");
			demoPost.demoRequestPost();

		};
	}

}
