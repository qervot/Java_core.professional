package hw;

import java.util.Arrays;

public class Task1and2 {

    //Массив после четверки
    public static int[] arrayAfter4(int[] in) {

        for (int i = in.length - 1; i >= 0; i--) {
            if (in[i] == 4) {
//                int[] out = new int[in.length - 1 - i];
//                System.arraycopy(in, in.length - i, out, 0, in.length - 1 - i);
//
//                return out;

                return Arrays.copyOfRange(in, i + 1, in.length);
            }
        }


        throw new RuntimeException();
    }

    //в массиве только 1 и 4
    public static boolean check14(int[] in) {
        boolean b1 = false;
        boolean b4 = false;

        for (int anIn : in) {

            if (anIn == 1) {
                b1 = true;
            } else if (anIn == 4) {
                b4 = true;
            } else {
                return false;
            }
        }

        return b1 && b4;

    }

    public static void main(String[] args) {
//        int[] x = {1, 2, 4, 2, 2, 4, 1};
//        int[] z = arrayAfter4(x);
//        System.out.println(Arrays.toString(z));

        System.out.println(check14(new int[]{1,4,4,1}));
    }
}
