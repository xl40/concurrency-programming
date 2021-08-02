package common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xuelin
 */

@Slf4j(topic = "c.AddTask")
public class CpuInfo {

    static final int nThreads = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        log.info(String.valueOf(countCpu()));
    }


    public static int countCpu(){

        //输出本机CPU的数量,是一个数字
        System.out.println( Runtime.getRuntime().availableProcessors());
        log.info("???? {}", Runtime.getRuntime().availableProcessors());
      return  Runtime.getRuntime().availableProcessors();

    }

}
