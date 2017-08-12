import hw.Task1and2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Task2Test {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1,1,4}, true},
                {new int[]{1,1,4,2,2}, false},
                {new int[]{1, 1, 1, 1}, false}
        });
    }

    private int[] in;
    private boolean out;

    public Task2Test(int[] in, boolean out) {
        this.in = in;
        this.out = out;
    }

    @Test
    public void TetsT2() {
        Assert.assertEquals(out, Task1and2.check14(in));
    }


}
