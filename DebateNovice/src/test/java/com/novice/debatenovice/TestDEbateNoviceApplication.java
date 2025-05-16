package com.novice.debatenovice;

import org.springframework.boot.SpringApplication;

public class TestDEbateNoviceApplication {

    public static void main(String[] args) {
        SpringApplication.from(DebateNoviceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
