package com.ufcg.psoft;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.service.user.UserBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.ufcg.psoft"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class EstoqueFacilRestApi {

	public static void main(String[] args) {
		SpringApplication.run(EstoqueFacilRestApi.class, args);
		
	}
}
