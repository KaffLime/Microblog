package com.artem.korolchuk.study.microblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.artem.korolchuk.study.microblog.repository")
@ComponentScan(basePackages = { "com.artem.korolchuk.study.microblog.*", "com.artem.korolchuk.study.microblog.controller"})
@EntityScan("com.artem.korolchuk.study.microblog.entity")
public class MicroblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroblogApplication.class, args);
	}
}
