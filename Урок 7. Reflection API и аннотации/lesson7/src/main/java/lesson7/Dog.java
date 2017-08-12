package lesson7;

//public class Dog implements Jumpable, Swimable {
public class Dog extends Animal{
    public int b;
    private int c;

    public Dog(int b, int c) {
        this.b = b;
        this.c = c;
    }

    public void info(String info) {
        System.out.println(info);
    }

    public void info(int info) {
        System.out.println(info);
    }

    private void voice() {
        System.out.println("voice");
    }
}
