package com.totvs.supplyagro.projetoreferencia.cadastros;

import com.totvs.tjf.api.jpa.repository.impl.ApiJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(repositoryBaseClass = ApiJpaRepositoryImpl.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	void setTimeZoneDefault() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
