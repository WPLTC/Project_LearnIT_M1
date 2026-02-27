package com.example.demo.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashCli {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java -jar ... <password>");
            System.exit(2);
        }
        String pwd = args[0];
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        System.out.println(enc.encode(pwd));
    }
}
