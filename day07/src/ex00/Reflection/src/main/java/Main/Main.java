package Main;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static Class<?> aClass;
    private static Field[] fields;
    private static Method[] methods;
    private static Object obj;
    private static Set<Class<?>> classes;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            scanClasses();
            enterClass();
            scanClass();
            createObject();
            changeField();
            callMethod();
        } catch (InstantiationException | IllegalArgumentException
                 | IllegalAccessException | NullPointerException
                 | ClassNotFoundException | InputMismatchException
                 | InvocationTargetException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void scanClasses() {
        System.out.println("Classes:");
        Reflections reflections = new Reflections("edu.school21.classes", new SubTypesScanner(false));
        classes = reflections.getSubTypesOf(Object.class);
        for(Class<?> i : classes) {
            System.out.println("- " + i.getSimpleName());
        }
        System.out.println("----------------------------");
    }

    private static void enterClass() throws IllegalArgumentException {
        System.out.println("Enter class name:");
        System.out.print("-> ");
        String className = null;
        if(!scanner.hasNext()) {
            throw new IllegalArgumentException("Valid class name has to be entered");
        }
        className = scanner.next();
        for (Class<?> clss : classes) {
            if (clss.getSimpleName().equals(className)) {
                aClass = clss;
                System.out.println("----------------------------");
                return;
            }
        }
        throw new IllegalArgumentException("No such class");
    }

    private static void scanClass() {
        fields = aClass.getDeclaredFields();
        methods = aClass.getDeclaredMethods();
        System.out.println("fields: ");
        for (Field field : fields) {
            System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
        }
        System.out.println("methods: ");
        for (Method method : methods) {
            System.out.println(printMethod(method));
        }
        System.out.println("----------------------------");
    }

    private static String printMethod(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String result = "";
        result += method.getReturnType().getSimpleName().equals("void") ? "\t " : "\t" + method.getReturnType().getSimpleName() + " ";
        result += method.getName() + "(";
        for (int i = 0; i < parameterTypes.length; i++) {
            result += parameterTypes[i].getSimpleName();
            result += i == parameterTypes.length - 1 ? "" : ", ";
        }
        result += ")";
        return result;
    }

    private static void createObject() throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InstantiationException {
        System.out.println("Let's create an object");
        obj = Class.forName(aClass.getName()).newInstance();
        for (Field field : fields) {
            System.out.print(field.getName() + ":\n-> ");
            if (scanner.hasNext()) {
                initializeField(field);
            }
        }
        System.out.println("Object created: " + obj);
        System.out.println("----------------------------");
    }

    private static void initializeField(Field field) throws IllegalAccessException, IllegalArgumentException, InputMismatchException {
        if (scanner.hasNext()) {
            field.setAccessible(true);
            switch (field.getType().getSimpleName()) {
                case "Integer":
                case "int":
                    field.set(obj, scanner.nextInt());
                    break;
                case "String":
                    field.set(obj, scanner.next());
                    break;
                case "Double":
                case "double":
                    field.set(obj, scanner.nextDouble());
                    break;
                case "Long":
                case "long":
                    field.set(obj, scanner.nextLong());
                    break;
                case "Float":
                case "float":
                    field.set(obj, scanner.nextFloat());
                    break;
                case "Boolean":
                case "boolean":
                    field.set(obj, scanner.nextBoolean());
                    break;
            }
        } else {
            throw new IllegalArgumentException("No value for initializing field");
        }
    }

    private static void changeField() throws IllegalAccessException, IllegalArgumentException {
        System.out.print("Enter name of the field for changing:\n-> ");
        String fieldName;
        if (scanner.hasNext()) {
            fieldName = scanner.next();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    System.out.print("Enter " + field.getType().getSimpleName() + " value:\n-> ");
                    initializeField(field);
                    System.out.println("Object updated " + obj);
                    System.out.println("----------------------------");
                    return;
                }
            }
            throw new IllegalArgumentException("No such field for updating");
        } else {
            throw new IllegalArgumentException("No such field for updating");
        }
    }

    private static void callMethod() throws InvocationTargetException, IllegalAccessException{
        System.out.println("Enter name of the method for call:");
        System.out.print("-> ");
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("No such method to call");
        }
        scanner.nextLine();
        String methodName = scanner.nextLine();
        System.out.println(methodName);
        for (Method method : methods) {
            if (methodNameToString(method).equals(methodName)) {
                method.setAccessible(true);
                callParticularMethod(method);
                return;
            }
        }
        throw new IllegalArgumentException("No such method to call");
    }

    private static String methodNameToString(Method method) {
        String actualMethodName = method.getName() + "(";
        Parameter[] params = method.getParameters();
        for (int i = 0; i < method.getParameterCount(); i++) {
            actualMethodName += params[i].getType().getSimpleName();
            actualMethodName += i == method.getParameterCount() - 1 ? "" : ", ";
        }
        actualMethodName += ")";
        return actualMethodName;
    }

    private static void callParticularMethod(Method method) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        ArrayList<Object> objects = new ArrayList<>();
        for (Class<?> param : method.getParameterTypes()) {
            switch (param.getSimpleName()) {
                case "Integer":
                case "int":
                    System.out.println("Enter integer value: ");
                    System.out.print("-> ");
                    objects.add(scanner.nextInt());
                    break;
                case "String":
                    System.out.println("Enter String value:");
                    System.out.print("-> ");
                    objects.add(scanner.nextLine());
                    break;
                case "Double":
                case "double":
                    System.out.println("Enter double value:");
                    System.out.print("-> ");
                    objects.add(scanner.nextDouble());
                    break;
                case "boolean":
                case "Boolean":
                    System.out.println("Enter boolean value:");
                    System.out.print("-> ");
                    objects.add(scanner.nextBoolean());
                    break;
                case "float":
                case "Float":
                    System.out.println("Enter float value:");
                    System.out.print("-> ");
                    objects.add(scanner.nextFloat());
                    break;
                case "long":
                case "Long":
                    System.out.println("Enter long value:");
                    System.out.print("-> ");
                    objects.add(scanner.nextLong());
                    break;
            }
        }
        System.out.println("Method returned:");
        if(method.getReturnType().getSimpleName().equals("void")) {
            method.invoke(obj, objects.toArray());
        } else {
            System.out.println(method.invoke(obj, objects.toArray()));
        }
    }
}