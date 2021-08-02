package principle;

/**
 * @author xuelin
 */
public class SynchronizedPrinciple {

    static final Object obj = new Object();

    public static void method1() {
        synchronized (obj) { // 同步块 A
            method2();
        }
    }

    public static void method2() {
        synchronized (obj) { // 同步块 B
        }
    }
}

