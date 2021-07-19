package methods;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * @author xuelin
 */
@Slf4j(topic = "c.commonMethodDemo.DiffSleepAndYield")
public class DiffSleepAndYield {

    public static void main(String[] args) {

        yieldTest();

    }

    public static void sleepStatusTest() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        log.info("t1 state: {}", t1.getState());

        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("t1 state: {}", t1.getState());
    }

    public static void interruptTest() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("enter sleep...");
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("interrupt...");
        t1.interrupt();
    }

    public static void format() throws InterruptedException {
        log.debug("enter");
        TimeUnit.SECONDS.sleep(1);
        log.debug("end");
//        Thread.sleep(1000);
    }

    public static void yieldTest() {
        Runnable task1 = () -> {
            int count = 0;
            for (; ; ) {
                log.info("---->1 " + count++);
            }
        };
        Runnable task2 = () -> {
            int count = 0;
            for (; ; ) {
                Thread.yield();
                System.out.println("              ---->2 " + count++);
            }
        };
        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
    }

    public static void sleepCpuLimitWhileTrue() {

        int j = 0;
        while (true) { // reachable end condition added
            j++;
            if (j == Integer.MIN_VALUE) { //// true at Integer.MAX_VALUE +1
                break;
            }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                log.warn(e.getMessage(), e);
                //Restore interrupted state...
                Thread.currentThread().interrupt();
            }

        }
        int k = 0;
        boolean b = true;
        while (b) {
            k++;
            b = k < Integer.MAX_VALUE;
        }

    }

    static int r = 0;

    private static void test1() throws InterruptedException {

        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束");
            r = 10;
        },"t1");
        t1.start();
        t1.join();
        log.debug("结果为:{}", r);
        log.debug("结束");
    }

}


