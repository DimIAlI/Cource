package org.example;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    private void start() {
        ProgramRunner runner = ProgramRunner.createRunner();
        runner.run();
    }
}
