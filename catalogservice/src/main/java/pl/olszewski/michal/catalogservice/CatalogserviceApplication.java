package pl.olszewski.michal.catalogservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.olszewski.michal.catalogservice.catalog.CatalogInfo;
import pl.olszewski.michal.catalogservice.catalog.CatalogInfoRepository;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class CatalogserviceApplication implements CommandLineRunner {

    private final CatalogInfoRepository catalogInfoRepository;

    public CatalogserviceApplication(CatalogInfoRepository catalogInfoRepository) {
        this.catalogInfoRepository = catalogInfoRepository;
    }

    @Bean
    public RestTemplate loadRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(CatalogserviceApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        catalogInfoRepository.save(new CatalogInfo(997L, true));
    }
}
