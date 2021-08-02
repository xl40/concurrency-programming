package methods;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static n2.util.Sleeper.sleep;

/**
 * @author xuelin
 */
@Slf4j(topic = "c.TestInterrupt")
public class TestInterrupt {
    public static void main(String[] args) throws InterruptedException {
        // test1();
        // test2();
//        test3();
         test4();
    }

    private static void test1() {
        log.info("示例一: 打断 sleep 的线程, 会清空打断状态，以 sleep 为例");
        Thread t1 = new Thread(() -> sleep(1), "t1");
        t1.start();
        sleep(0.5);
        t1.interrupt();
        log.debug(" isInterrupted 状态: {}", t1.isInterrupted());
    }

    private static void test2() {
        log.info("打断正常运行的线程,  不会清空打断状态");
        Thread t2 = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                if (interrupted) {
                    log.debug(" isInterrupted 状态: {}", interrupted);
                    break;
                }
            }
        }, "t2");

        t2.start();
        sleep(0.5);
        t2.interrupt();
    }

    private static void test3() {
        log.info("打断 park 线程, 不会清空打断状态");
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug(" isInterrupted 状态：{}", Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        sleep(0.5);
        t1.interrupt();
    }

    private static void test4() {
        log.info("如果打断标记已经是 true, 则 park 会失效");
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park();
                log.debug("打断状态：{}", Thread.interrupted());
            }
        });
        t1.start();

        sleep(1);
        t1.interrupt();
    }

}
