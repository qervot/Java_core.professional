import hw.Task1and2;
import org.junit.Test;

/**
 * Created by nicol on 03.06.2017.
 */
public class Task1Exc {

    @Test(expected = RuntimeException.class)
    public void RETest() {
        Task1and2.arrayAfter4(new int[]{1, 1, 1});
    }
}
