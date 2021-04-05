import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestManager {
    public static void main(String[] args) {
        start(ClassForTests.class);
    }

    public static void start(Class testClass) {
        Method[] methods = testClass.getDeclaredMethods();
        List<Method> methodList = new ArrayList<>(Arrays.asList(methods));

        Method beforeSuiteMethod = findMethodAndSingleCheck(methodList, BeforeSuite.class);
        Method afterSuiteMethod = findMethodAndSingleCheck(methodList, AfterSuite.class);

        List<Method> testMethodList = createTestMethodList(methodList);

        sortTestMethodListByPriority(testMethodList);

        executeMethod(testClass, beforeSuiteMethod);

        for (Method o : testMethodList) {
            executeMethod(testClass, o);
        }

        executeMethod(testClass, afterSuiteMethod);
    }

    private static Method findMethodAndSingleCheck(List<Method> methodList, Class annotationClass) {
        Method resultMethod = null;
        for (Method o : methodList) {
            if (o.getAnnotation(annotationClass) != null) {
                if (resultMethod == null) {
                    resultMethod = o;
                } else {
                    throw new RuntimeException("Дублирование метода " + annotationClass.getSimpleName());
                }
            }
        }
        return resultMethod;
    }

    private static List<Method> createTestMethodList(List<Method> methodList) {
        List<Method> testMethodList = new ArrayList<>();
        for (Method o : methodList) {
            if (o.getAnnotation(Test.class) != null) {
                testMethodList.add(o);
            }
        }
        return testMethodList;
    }

    private static void sortTestMethodListByPriority(List<Method> methodList) {
        methodList.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Method m1 = (Method) o1;
                Method m2 = (Method) o2;
                return m2.getAnnotation(Test.class).priority() - m1.getAnnotation(Test.class).priority();
            }
        });
    }

    private static void executeMethod(Class testClass, Method method) {
        try {
            Object o = testClass.newInstance();
            method.invoke(o);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
