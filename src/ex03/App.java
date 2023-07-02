package ex03;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {

    public static Set<Class> componentScan(String pkg) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Set<Class> classes = new HashSet<>();

        URL packageUrl = classLoader.getResource(pkg);
        File packageDirectory = new File(packageUrl.toURI());
        for (File file : packageDirectory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                String className = pkg + "." + file.getName().replace(".class", "");
                //System.out.println(className);
                Class cls = Class.forName(className);
                classes.add(cls);
            }
        }
        return classes;
    }

    public static void findUri(Set<Class> classes, String uri) throws Exception {
        boolean isFind = false;
        for (Class cls : classes) {
            if (cls.isAnnotationPresent(Controller.class)) {
                Object instance = cls.newInstance();
                Method[] methods = cls.getDeclaredMethods();

                for (Method mt : methods) {
                    Annotation anno = mt.getDeclaredAnnotation(RequestMapping.class);
                    RequestMapping rm = (RequestMapping) anno;
                    if (rm.uri().equals(uri)) {
                        isFind = true;
                        mt.invoke(instance);
                    }
                }
            }
        }
        if(isFind == false){
            System.out.println("404 Not Found");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String uri = sc.nextLine();

        Set<Class> classes = componentScan("ex03");
        findUri(classes, uri);

    }

}
