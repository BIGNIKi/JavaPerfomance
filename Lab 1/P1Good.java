package Task1;

public class P1Good
{
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 10000; ++i) {
            s.append(" ").append(i);
        }
        System.out.println(s.length());
    }
}
