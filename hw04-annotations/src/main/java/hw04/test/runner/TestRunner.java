package hw04.test.runner;

import hw04.test.annotations.After;
import hw04.test.annotations.Before;
import hw04.test.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {

    public static void run(String testClassname) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        var methods = getMethods(testClassname);
        var totalNumberOfTests = methods.get(Test.class).size();
        var numberOfPassedTests = 0;
        var numberOfFailedTests = 0;
        for (Method testMethod : methods.get(Test.class)) {
            var testPassed = runTest(Class.forName(testClassname), methods.get(Before.class), methods.get(After.class), testMethod);
            if (testPassed) {
                numberOfPassedTests++;
            }
            else {
                numberOfFailedTests++;
            }
        }

        System.out.println();
        System.out.println(String.format("Total number of tests: %5d", totalNumberOfTests));
        System.out.println(String.format("Number of passed tests: %4d", numberOfPassedTests));
        System.out.println(String.format("Number of failed tests: %4d", numberOfFailedTests));
    }

    private static Map<Class, List<Method>> getMethods(String testClassname) throws ClassNotFoundException {

        var methods = new HashMap<Class, List<Method>>();
        methods.put(Before.class, new ArrayList<>());
        methods.put(After.class, new ArrayList<>());
        methods.put(Test.class, new ArrayList<>());

        Class<?> clazz = Class.forName(testClassname);
        for (var method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                methods.get(Before.class).add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                methods.get(After.class).add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                methods.get(Test.class).add(method);
            }
        }

        return methods;
    }

    private static boolean runTest(Class testClass, List<Method> beforeMethods, List<Method> afterMethods, Method testMethod) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Object testObject = testClass.getDeclaredConstructor().newInstance();

        runBeforeMethods(testObject, beforeMethods);

        var testResult = runTestMethod(testObject, testMethod);

        runAfterMethods(testObject, afterMethods);

        return testResult;
    }

    private static void runBeforeMethods(Object testObject, List<Method> beforeMethods) throws InvocationTargetException, IllegalAccessException {
        for(var beforeMethod : beforeMethods) {
            beforeMethod.setAccessible(true);
            beforeMethod.invoke(testObject);
        }
    }

    private static boolean runTestMethod(Object testObject, Method testMethod) throws IllegalAccessException {
        try {
            testMethod.setAccessible(true);
            testMethod.invoke(testObject);

            return true;
        }
        catch (InvocationTargetException e) {
            return false;
        }
    }

    private static void runAfterMethods(Object testObject, List<Method> afterMethods) throws InvocationTargetException, IllegalAccessException {
        for(var afterMethod : afterMethods) {
            afterMethod.setAccessible(true);
            afterMethod.invoke(testObject);
        }
    }
}
