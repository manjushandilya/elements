package com.ads.elements;

import com.ads.elements.model.Role;
import com.ads.elements.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClientApplication {

    public static void main(String[] args) {
        try (final ConfigurableApplicationContext run = SpringApplication.run(ClientApplication.class, args)) {
            System.out.println("Application running...");
        }
    }

    @Bean
    CommandLineRunner init(final RoleRepository roleRepository) {

        return args -> {

            final Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                final Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            final Role clientRole = roleRepository.findByRole("CLIENT");
            if (clientRole == null) {
                final Role newClientRole = new Role();
                newClientRole.setRole("CLIENT");
                roleRepository.save(newClientRole);
            }
        };

    }

}
