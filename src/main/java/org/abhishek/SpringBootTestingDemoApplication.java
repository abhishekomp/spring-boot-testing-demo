package org.abhishek;

import jakarta.annotation.PostConstruct;
import org.abhishek.model.Product;
import org.abhishek.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class SpringBootTestingDemoApplication {

	@Autowired
	private ProductRepository productRepository;

	@Value("${myapp.name}")
	private String myAppName;

	public static void main(String[] args) {
		System.out.println(">>>>>>SpringBootTestingDemoApplication::main()");
		//System.out.println(">>>>>>SpringBootTestingDemoApplication::main() with myAppName: " + myAppName);
		SpringApplication.run(SpringBootTestingDemoApplication.class, args);
	}

	@Bean
	public CommonsRequestLoggingFilter commonsRequestLoggingFilter(){
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeHeaders(true);
		filter.setIncludeClientInfo(true);
		filter.setIncludePayload(true);
		filter.setIncludeQueryString(true);
		filter.setAfterMessagePrefix("REQUEST DATA : ");
		return filter;
	}

	@PostConstruct
	public void initializeProductDB(){
		System.out.println(">>>>>>SpringBootTestingDemoApplication::initializeProductDB() with appName: " + myAppName);
		Product appleIPhone2024 = new Product(100001L, "APPLE_iPHONE_2024_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.now(), LocalDate.now());
		Product appleIPhone2023 = new Product(100002L, "APPLE_iPHONE_2023_256GB", 16999.0, 10, "WELCOME_SPRING", LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
		Product appleIPhone2022 = new Product(100003L, "APPLE_iPHONE_2022_256GB", 15999.0, 10, "WELCOME_AUTUMN", LocalDate.now(), LocalDate.now());
		Product appleIPhone2021 = new Product(100004L, "APPLE_iPHONE_2021_256GB", 14999.0, 10, "WELCOME_SUMMER", LocalDate.now(), LocalDate.now());
		Product appleIPhone2020 = new Product(100005L, "APPLE_iPHONE_2020_256GB", 13999.0, 20, "WELCOME_SPRING", LocalDate.now(), LocalDate.now());

		productRepository.saveAll(
				Stream.of(appleIPhone2024, appleIPhone2023, appleIPhone2022, appleIPhone2021, appleIPhone2020)
					.collect(toList()));
	}

}
