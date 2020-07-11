package test;

public class AsmTest
{
    public static void method() {
        boolean bool = true;
        if (!bool)
            return;

        if (!bool)
            return;

        if (!bool)
            return;

        System.out.println("Xd");
    }
}