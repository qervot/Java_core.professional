import org.junit.*;
import testing.Calculator;

public class CalcTest {

    private static Calculator calculator;

    @BeforeClass
    public static void startTest() {
        System.out.println("A");
        calculator = new Calculator();
    }

    @AfterClass
    public static void afterMethod() {
        System.out.println("After");
    }

    //@Ignore
    @Test(timeout = 500)
    public void testAdd() {
        //Assert.assertEquals(4, calculator.add(2,2));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (calculator.add(2,2) != 4) {
            Assert.fail();
        }
    }

    @Test
    public void testSub() {
        Assert.assertEquals(3, calculator.sub(5,2));
    }

    @Test
    public void testMul() {
        Assert.assertEquals(9, calculator.mul(3,3));
    }

    @Test
    public void testDiv() {
        Assert.assertEquals(1, calculator.div(2,2));
    }

    @Test(expected = ArithmeticException.class)
    public void testDivZero() {
        calculator.div(10,0);
    }
}
