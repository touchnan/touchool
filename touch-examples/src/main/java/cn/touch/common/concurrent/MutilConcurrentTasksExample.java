package cn.touch.common.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by <a href="mailto:88052350@qq.com">touchnan</a> on 2016/3/25.
 */
public class MutilConcurrentTasksExample {
    public static void main(String[] args) {
        simple();
        traditional();
        System.exit(0);
    }
    public static void simple() {
        int taskSize = 5;
        ExecutorService executor = Executors.newFixedThreadPool(taskSize);
        // 构建完成服务
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executor);
        for (int i = 1; i <= taskSize; i++) {
            int sleep = taskSize - i; // 睡眠时间
            int value = i; // 返回结果
            // 向线程池提交任务
            completionService.submit(() -> {
                TimeUnit.SECONDS.sleep(sleep);
                return value;
            });
        }

        // 按照完成顺序,打印结果
        for (int i = 0; i < taskSize; i++) {
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        // 所有任务已经完成,关闭线程池
        System.out.println("all over.");
        executor.shutdown();
    }

    public static void traditional() {
        int taskSize = 5;
        ExecutorService executor = Executors.newFixedThreadPool(taskSize);
        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
        for (int i = 1; i <= taskSize; i++) {
            int sleep = taskSize - i; // 睡眠时间
            int value = i; // 返回结果
            // 向线程池提交任务
            Future<Integer> future = executor.submit(() -> {
                TimeUnit.SECONDS.sleep(sleep);
                return value;
            });
            // 保留每个任务的Future
            futureList.add(future);
        }
        // 轮询,获取完成任务的返回结果
        while (taskSize > 0) {
            for (Future<Integer> future : futureList) {
                Integer result = null;
                try {
                    result = future.get(0, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    // 超时异常需要忽略,因为我们设置了等待时间为0,只要任务没有完成,就会报该异常
                }
                // 任务已经完成
                if (result != null) {
                    System.out.println("result=" + result);
                    // 从future列表中删除已经完成的任务
                    futureList.remove(future);
                    taskSize--;
                    //此处必须break，否则会抛出并发修改异常。（也可以通过将futureList声明为CopyOnWriteArrayList类型解决）
                    break; // 进行下一次while循环
                }
            }
        }

        // 所有任务已经完成,关闭线程池
        System.out.println("all over.");
        executor.shutdown();
    }
}
