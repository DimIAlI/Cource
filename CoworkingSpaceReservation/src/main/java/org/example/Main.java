package org.example;

import lombok.SneakyThrows;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    @SneakyThrows
    private void start() {
        ProgramRunner runner = ProgramRunner.createRunner();
        runner.run();
    }
}
