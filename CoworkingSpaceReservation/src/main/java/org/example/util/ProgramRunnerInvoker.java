package org.example.util;

import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class ProgramRunnerInvoker {
    private static final String CLASS_NAME = "ProgramRunner";
    private static final String INIT_METHOD_NAME = "createRunner";
    private static final String RUN = "run";
    private final Object runnerInstance;

    public ProgramRunnerInvoker(String classpath) {
        Class<?> programRunnerClass = loadClassFromClasspath(classpath);
        runnerInstance = invokeFactoryMethod(programRunnerClass);
    }

    @SneakyThrows
    private Class<?> loadClassFromClasspath(String classpath) {
        CustomClassLoader customClassLoader = new CustomClassLoader(classpath);
        return customClassLoader.loadClass(CLASS_NAME);
    }

    @SneakyThrows
    private Object invokeFactoryMethod(Class<?> clazz) {
        Method method = clazz.getMethod(INIT_METHOD_NAME);
        return method.invoke(null);
    }

    @SneakyThrows
    public void run() {
        invokeMethod(runnerInstance);
    }

    @SneakyThrows
    private void invokeMethod(Object instance) {
        Method method = instance.getClass().getMethod(RUN);
        method.invoke(instance);
    }
}
