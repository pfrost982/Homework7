public class ClassForTests {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Подготовка к тестированию...");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Подготовка к тестированию...");
    }

    @Test(priority = 7)
    public void test_1() {
        System.out.println("Тест 1, приоритет 7");
    }

    @Test(priority = 3)
    public void test_2() {
        System.out.println("Тест 2, приоритет 3");
    }

    @Test(priority = 5)
    public void test_3() {
        System.out.println("Тест 3, приоритет 5");
    }

    @Test(priority = 1)
    public void test_4() {
        System.out.println("Тест 4, приоритет 1");
    }
}

