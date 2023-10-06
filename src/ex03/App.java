package ex03;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {

    public static Set<Object> componentScan(String pkg) throws Exception {
        System.out.println("-------------------- 컴포넌트 스캔 start");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Set<Object> instances = new HashSet<>();

        URL packageUrl = classLoader.getResource(pkg);
        File packageDirectory = new File(packageUrl.toURI());
        for (File file : packageDirectory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                String className = pkg + "." + file.getName().replace(".class", "");
                System.out.println(className);
                Class cls = Class.forName(className);
                if (cls.isAnnotationPresent(Controller.class)) {
                    System.out.println("스캔완료 : "+cls);
                    Object instance = cls.newInstance();
                    instances.add(instance);
                }
            }
        }
        System.out.println("-------------------- 컴포넌트 스캔 end");
        return instances;
    }

    public static void findUri(Set<Object> instances, String uri) throws Exception {
        boolean isFind = false;
        for (Object instance : instances) {
            Class cls = instance.getClass();

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
        if (isFind == false) {
            System.out.println("404 Not Found");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String uri = sc.nextLine();

        Set<Object> instances = componentScan("ex03");
        findUri(instances, uri);

    }

}
