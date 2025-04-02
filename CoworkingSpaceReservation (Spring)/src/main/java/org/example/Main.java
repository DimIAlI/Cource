package org.example;

import lombok.SneakyThrows;
import org.example.config.ApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    @SneakyThrows
    private void start() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        DatabaseInitializer initializer = context.getBean(DatabaseInitializer.class);
        ProgramRunner runner = context.getBean(ProgramRunner.class);

        initializer.initDatabase();
        runner.run();
    }
}
