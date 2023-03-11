package ex02;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {


    public static void findUri(UserController uc, String uri) throws Exception {
        boolean isFind = false;
        Method[] methods = uc.getClass().getDeclaredMethods();

        for (Method mt : methods) {
            Annotation anno = mt.getDeclaredAnnotation(RequestMapping.class);
            RequestMapping rm = (RequestMapping) anno;
            if (rm.uri().equals(uri)) {
                isFind = true;
                mt.invoke(uc);
            }
        }
        if(isFind == false){
            System.out.println("404 Not Found");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String uri = sc.nextLine();

        findUri(new UserController(), uri);

    }

}
