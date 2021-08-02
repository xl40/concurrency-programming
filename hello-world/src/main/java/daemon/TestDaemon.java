package daemon;

import lombok.extern.slf4j.Slf4j;

import static n2.util.Sleeper.sleep;

@Slf4j(topic = "c.TestDaemon")
public class TestDaemon {
    public static void main(String[] args) {
        log.debug("main 开始运行...");

        Thread t1 = new Thread(() -> {
            while (true) {
                sleep(0.1);
                log.debug("守护进程状态 {}", Thread.currentThread().getState());
            }
        }, "daemon");

        // 设置该线程为守护线程
        t1.setDaemon(true);
        t1.start();
        log.debug("main 状态... {}",Thread.currentThread().getState());
        sleep(1);
        log.debug("main 状态... {}",Thread.currentThread().getState());
        log.debug("守护进程状态 {}", t1.getState());
        log.debug("main 运行结束... {}",Thread.currentThread().getState());
        log.debug("守护进程状态 {}", t1.getState());
    }
}
