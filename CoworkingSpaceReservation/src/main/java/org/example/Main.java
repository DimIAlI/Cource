package org.example;

import lombok.SneakyThrows;
import org.example.util.ProgramRunnerInvoker;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    @SneakyThrows
    private void start() {

        ProgramRunnerInvoker programRunnerInvoker = new ProgramRunnerInvoker("lib");
        programRunnerInvoker.run();
    }
}
