package edu.lieineyes.app;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Main {
    private static final String PACKAGE_NAME = "edu.lieineyes.classes";
    private static final String MESSAGE_NAME_CLASSES = "Classes:";
    private static final String DELIMITER = "---------------------";
    private static final String MESSAGE_INPUT_NAME_CLASS = "Enter class name:";
    private static final String MESSAGE_NAME_FIELDS = "fields:";
    private static final String MESSAGE_NAME_METHODS = "methods:";
    private static final String MESSAGE_CREATE_OBJECT = "Let’s create an object.";
    private static final String MESSAGE_CREATE_OBJECT_SUCCESS = "Object created: ";
    private static final String MESSAGE_CHANGE_FIELDS = "Enter name of the field for changing:";
    private static final String MESSAGE_CHANGE_FIELDS_SUCCESS = "Object updated: ";
    private static final String MESSAGE_INPUT_USER_VALUE = "Enter %s value:\n";
    private static final String MESSAGE_CALL_METHOD = "Enter name of the method for call:";
    private static final String MESSAGE_RETURN_METHOD = "Method returned:";

    private static final Scanner SC = new Scanner(System.in);

    private static String name_class;
    private static Class clazz;
    private static Field[] fields;
    private static Method[] methods;
    private static Object newObject;


    public static void main(String[] args) {
        printAllClasses();
        printInformationByClass();
        createObject();
        changedField();
        callMethod();
        SC.close();
    }

    private static void printAllClasses() {
        Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner(false));
        Set<Class> allClasses = new HashSet<>(reflections.getSubTypesOf(Object.class));
        System.out.println(MESSAGE_NAME_CLASSES);
        for (Class<?> aClass : allClasses) {
            System.out.println("\t- " + aClass.getSimpleName());
        }
        System.out.println(DELIMITER);
    }

    private static void printInformationByClass() {
        System.out.println(MESSAGE_INPUT_NAME_CLASS);
        name_class = SC.nextLine();
        System.out.println(DELIMITER);
        initClazzFieldsMethods();
        printInformationByFieldsAndMethods();
        System.out.println(DELIMITER);
    }

    private static void initClazzFieldsMethods() {
        try {
            clazz = Class.forName(PACKAGE_NAME + "." + name_class);
            fields = clazz.getDeclaredFields();
            methods = clazz.getDeclaredMethods();
            newObject = clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println(e);
            System.exit(-1);
        }
    }

    private static void printInformationByFieldsAndMethods() {
        System.out.println(MESSAGE_NAME_FIELDS);
        for (Field field : fields) {
            System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
        }
        System.out.println(MESSAGE_NAME_METHODS);
        for (Method method : methods) {
            System.out.println("\t" + method.getReturnType().getSimpleName() + " " + method.getName());
        }
    }

    private static void createObject() {
        System.out.println(MESSAGE_CREATE_OBJECT);
        for (Field field : fields) {
            System.out.println(field.getName() + ":");
            field.setAccessible(true);
            setterAllFields(field);
            field.setAccessible(false);
        }
        System.out.println(MESSAGE_CREATE_OBJECT_SUCCESS + newObject);
        System.out.println(DELIMITER);
    }

    private static void setterAllFields(Field field) {
        try {
            switch (field.getType().getSimpleName()) {
                case ("String"):
                    field.set(newObject, SC.next());
                    break;
                case ("int"):
                case ("Integer"):
                    field.set(newObject, SC.nextInt());
                    break;
                case ("long"):
                case ("Long"):
                    field.set(newObject, SC.nextDouble());
                    break;
                case ("double"):
                case ("Double"):
                    field.set(newObject, SC.nextFloat());
                    break;
                case ("boolean"):
                case ("Boolean"):
                    field.set(newObject, SC.nextBoolean());
                    break;
            }
        } catch (IllegalAccessException e) {
            System.out.println(e);
            System.exit(-1);
        }
    }

    private static void changedField() {
        System.out.println(MESSAGE_CHANGE_FIELDS);
        String fieldName = SC.next();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                System.out.printf(MESSAGE_INPUT_USER_VALUE, field.getType().getSimpleName());
                setterAllFields(field);
                field.setAccessible(false);
            }
        }
        System.out.println(MESSAGE_CHANGE_FIELDS_SUCCESS + newObject);
        System.out.println(DELIMITER);
    }

    private static void callMethod() {
        System.out.println(MESSAGE_CALL_METHOD);
        String methodName = SC.next();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                method.setAccessible(true);
                Parameter[] parameters = method.getParameters();
                Object[] arguments = new Object[method.getParameterCount()];
                for (int i = 0; i < method.getParameterCount(); ++i) {
                    System.out.println("Enter " + parameters[i].getType().getSimpleName() + " value:");
                    switchInputType(parameters, arguments, i);
                }
                try {
                    if (method.getReturnType().getSimpleName().equals("void")) {
                        method.invoke(newObject, arguments);
                    } else {
                        System.out.println(MESSAGE_RETURN_METHOD);
                        System.out.println(method.invoke(newObject, arguments));
                    }
                    method.setAccessible(false);
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    System.out.println(e);
                    System.exit(-1);
                }
            }
        }
    }

    private static void switchInputType(Parameter[] parameters, Object[] arguments, int i) {
        switch (parameters[i].getType().getSimpleName()) {
            case ("String"):
                arguments[i] = SC.next();
                break;
            case ("int"):
            case ("Integer"):
                arguments[i] = SC.nextInt();
                break;
            case ("long"):
            case ("Long"):
                arguments[i] = SC.nextLong();
                break;
            case ("double"):
            case ("Double"):
                arguments[i] = SC.nextDouble();
                break;
            case ("boolean"):
            case ("Boolean"):
                arguments[i] = SC.nextBoolean();
                break;
        }
    }

}
