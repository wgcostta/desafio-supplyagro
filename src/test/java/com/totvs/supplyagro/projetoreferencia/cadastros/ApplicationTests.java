package com.totvs.supplyagro.projetoreferencia.cadastros;


import com.totvs.tjf.api.jpa.repository.impl.ApiJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EntityScan(basePackages = "com.totvs.supplyagro.projetoreferencia.cadastros")
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = ApiJpaRepositoryImpl.class, basePackages = "com.totvs.supplyagro.projetoreferencia.cadastros")
public class ApplicationTests {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTests.class, args);
    }
}
