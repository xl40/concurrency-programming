package methods;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xuelin
 */
@Slf4j(topic = "c.StartVsRun")
public class StartVsRun {
    public static void main(String[] args) {

        final Thread thread1 = new Thread(() -> log.info("do something"));

        final Thread thread2 = new Thread(() -> log.info("do something"));

        //直接调用 run 是在主线程中执行了 run，没有启动新的线程
        thread1.run();
        //使用 start 是启动新的线程，通过新的线程间接执行 run 中的代码
        thread2.start();

        /*
            ╔══════════════════════════════════════════════════════╗
            ║ ► Console                                            ║
            ╠══════════════════════════════════════════════════════╣
            ║ 17:01:11.509 c.StartVsRun [main] - do something      ║
            ║ 17:01:11.510 c.StartVsRun [Thread-1] - do something  ║
            ╚══════════════════════════════════════════════════════╝
         */

    }
}