package com.youssef.GridPulse.configuration;

import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.auth.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("docker")
@Slf4j
public class DockerInitializer {

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService authService) {
        return args -> {
            // Admin user
            var adminEmail = "admin@gridpulse.io";
            if (!authService.isEmailAvailable(adminEmail)) {
                var adminInput = RegisterInput.builder()
                        .firstname("Admin")
                        .lastname("User")
                        .email(adminEmail)
                        .password(System.getenv().get("ADMIN_PASS"))
                        .build();

                var admin = authService.createUserWithRole(adminInput, "ADMIN");
                log.info("✅ Admin seeded: {}", adminEmail);
                log.info("🔑 Admin access token: {}", admin.getAccessToken());
                log.info("🔁 Admin refresh token: {}", admin.getRefreshToken());
            }

            // Youssef user
            var ysfEmail = "youssef@gridpulse.io";
            if (!authService.isEmailAvailable(ysfEmail)) {
                var ysfInput = RegisterInput.builder()
                        .firstname("Youssef")
                        .lastname("Ammari")
                        .email(ysfEmail)
                        .password(System.getenv().get("YSF_PASS"))
                        .build();

                var ysf = authService.register(ysfInput);
                log.info("✅ Youssef seeded: {}", ysfEmail);
                log.info("🔑 YSF access token: {}", ysf.getAccessToken());
                log.info("🔁 YSF refresh token: {}", ysf.getRefreshToken());
            }
        };
    }
}