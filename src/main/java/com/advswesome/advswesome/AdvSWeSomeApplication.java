package com.advswesome.advswesome;

import com.google.cloud.spring.data.firestore.repository.config.EnableReactiveFirestoreRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableReactiveFirestoreRepositories
public class AdvSWeSomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvSWeSomeApplication.class, args);
	}

}
