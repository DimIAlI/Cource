package org.example.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;

@UtilityClass
public class ProgramRunnerInvoker {
    private final String CLASS_NAME = "ProgramRunner";
    private final String INIT_METHOD_NAME = "createRunner";
    private final String RUN = "run";
    private Object runnerInstance;

    @SneakyThrows
    public void initialize(String classpath) {
        Class<?> programRunnerClass = loadClassFromClasspath(classpath);
        runnerInstance = invokeFactoryMethod(programRunnerClass);
    }

    @SneakyThrows
    public void run() {
        invokeMethod(runnerInstance);
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
    private void invokeMethod(Object instance) {
        Method method = instance.getClass().getMethod(RUN);
        method.invoke(instance);
    }
}
