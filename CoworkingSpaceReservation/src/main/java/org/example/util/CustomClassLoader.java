package org.example.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader {

    private final String classpath;

    public CustomClassLoader(String classpath) {
        this.classpath = classpath.endsWith(File.separator) ? classpath : classpath + File.separator;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException("Class " + name + " not found");
        }
        return defineClass(name, classData, 0, classData.length);
    }

    private byte[] loadClassData(String className) {
        String path = classpath + File.separator + className + ".class";
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return null;
        }
    }
}


