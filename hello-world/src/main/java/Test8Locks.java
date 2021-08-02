import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.*;

import static com.azul.crs.shared.Utils.sleep;

@Slf4j(topic = "c.Test8Locks")
public class Test8Locks {
    public static void main(String[] args) {
        start6();
    }

    private static ExecutorService singleThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        return new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    private static void start1() {
        final Number n = new Number();
        singleThreadPool().execute(n::log3);
        singleThreadPool().execute(n::log2);
    }

    private static void start2() {
        final Number n = new Number();
        singleThreadPool().execute(n::sleepLog1);
        singleThreadPool().execute(n::log2);
    }

    private static void start3() {
        final Number n = new Number();
        singleThreadPool().execute(n::log1);
        singleThreadPool().execute(n::log2);
        singleThreadPool().execute(n::log3NoLock);
    }

    private static void start4() {
        final Number n1 = new Number();
        final Number n2 = new Number();
        singleThreadPool().execute(n1::sleepLog1);
        singleThreadPool().execute(n2::log2);
    }

    /**
     * 情况5:2 1s 后 1
     * TODO::??
     */
    private static void start5() {
        final Number n = new Number();
        singleThreadPool().execute(Number::staticSleepLog1);
        singleThreadPool().execute(n::log2);
    }

    /**
     * 情况6:1s 后12， 或 2 1s后 1
     */
    private static void start6() {
        final Number n = new Number();
        singleThreadPool().execute(Number::staticSleepLog1);
        singleThreadPool().execute(Number::staticLog2);
    }

    /**
     * 情况7:2 1s 后 1
     */
    private static void start7() {
        Number n1 = new Number();
        Number n2 = new Number();
        singleThreadPool().execute(Number::staticSleepLog1);
        singleThreadPool().execute(n2::log2);
    }

    /**
     * 情况8:1s 后12， 或 2 1s后 1
     */
    private static void start8() {
        Number n1 = new Number();
        Number n2 = new Number();
        singleThreadPool().execute(Number::staticSleepLog1);
        singleThreadPool().execute(Number::staticLog2);
    }

}

@Slf4j(topic = "c.Number")
class Number {

    @SneakyThrows
    public static synchronized void staticSleepLog1() {
        TimeUnit.SECONDS.sleep(1);
        log.debug("1");
    }

    public static synchronized void staticLog2() {
        log.debug("2");
    }

    public synchronized void sleepLog1() {
        sleep(1);
        log.debug("1");
    }

    public synchronized void log1() {
        log.debug("1");
    }

    public synchronized void log2() {
        log.debug("2");
    }

    public synchronized void log3() {
        log.debug("3");
    }

    public void log3NoLock() {
        log.debug("3");
    }

}

class ThreadUnsafe {

    ArrayList<String> list = new ArrayList<>();

    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) { // { 临界区, 会产生竞态条件 method2();
            method3();
            // } 临界区 }
        }
    }

    private void method2() {
        list.add("1");
    }

    private void method3() {
        list.remove(0);
    }
}

