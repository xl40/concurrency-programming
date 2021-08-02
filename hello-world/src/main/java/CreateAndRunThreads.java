
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author xuelin
 */
@Slf4j(topic = "c.ThreadStarter")
public class CreateAndRunThreads {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        createThreadWithAnonymous();
//        createThreadWithLambda();
//        createThreadWithRunnable();
        createThreadWithFutureTask();
        // 创建任务对象

    }


    /**
     *  线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。
     *  说明：使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销，解决资源不足的问题。
     *  如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。
     */
    public static void startThreadWithFactory(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
        singleThreadPool.shutdown();
    }



    public static void createThreadWithAnonymous() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                log.debug("ThreadWithAnonymous running");
            }
        };
        thread.setName("ThreadWithAnonymous");
        startThreadWithLog(thread);
    }

    public static void createThreadWithLambda() {
        Thread thread = new Thread(()-> log.debug("ThreadWithLambda running"), "ThreadWithLambda");
        startThreadWithLog(thread);
    }

    public static void createThreadWithRunnable(){
        Runnable r = () -> log.debug("ThreadWithRunnable running");
        Thread thread = new Thread(r, "ThreadWithRunnable");
        startThreadWithLog(thread);

    }

    public static void createThreadWithFutureTask() throws ExecutionException, InterruptedException {
        // 创建任务对象
        FutureTask<Integer> task3 = new FutureTask<>(() -> {
            log.debug("ThreadWithFutureTask running");
            Thread.sleep(5000);
            log.info("任务执行完毕, 返回执行结果");
            return 100;
        });

        // 参数1 是任务对象; 参数2 是线程名字，推荐
        startThreadWithLog(new Thread(task3, "ThreadWithFutureTask"));

        // 主线程阻塞，同步等待 task 执行完毕的结果
        log.info("The futureTask result is  {}",task3.get());

    }


    public static void startThreadWithLog(Thread thread){
        log.info("启动线程: " + thread.getName());
        thread.start();
    }


    /**
     * 自定义实现 runnable 方式创建线程
     */
    public static void customRunnable(){

    }

    public static void customFutureTask(){

    }

}
