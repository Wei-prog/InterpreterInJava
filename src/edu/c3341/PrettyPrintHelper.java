package edu.c3341;

public class PrettyPrintHelper {
    private static int tabCount;

    public static void setToOne() {
        tabCount = 1;
    }

    public static void increment() {
        tabCount++;
    }

    public static void decrement() {
        tabCount--;
    }

    public static void printTabs() {
        for (int i = 0; i < tabCount; i++) {
            System.out.print("\t");
        }
    }
}
