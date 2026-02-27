package com.example.demo.tools;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("tool")
public class PasswordHashGenerator implements CommandLineRunner {

    private final PasswordEncoder encoder;

    public PasswordHashGenerator(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        for (String arg : args) {
            if (arg.startsWith("--pwd=")) {
                String pwd = arg.substring("--pwd=".length());
                System.out.println(encoder.encode(pwd));
            }
        }
        // exit after printing
        System.exit(0);
    }
}
