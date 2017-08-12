import hw.Task1and2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Task1Test {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {new int[]{1, 1, 4}, new int[0]},
                        {new int[]{1, 1, 4, 2, 2}, new int[]{2, 2}}
                });
    }

    private int[] in;
    private int[] out;

    public Task1Test(int[] in, int[] out) {
        this.in = in;
        this.out = out;
    }

    @Test
    public void massTestAdd() {
        Assert.assertArrayEquals(out, Task1and2.arrayAfter4(in));
    }
}
