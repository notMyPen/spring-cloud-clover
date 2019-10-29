package rrx.cnuo.cncommon.accessory.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 解决线程池优雅停机时某些springbean销毁带来的问题：<br>
 * 在某些情况下，线程池在等待优雅停机过程中，spring其他bean已经被销毁，线程会报错运行。<br>
 * 解决的办法就是利用ApplicationListener<ContextClosedEvent> 来监听spring上下文关闭事件，该事件发生在上下文和spring bean销毁之前，此时手动执行线程池优雅停机<br>
 * 总的实现方法如下：<br>
 * 使用该listener就像手动配置了WaitForTasksToCompleteOnShutdown和AwaitTerminationSeconds一样(当然此时GlobalBeanConfig中不需要配置这两个属性)，只是保证了线程池优雅停机处理spring销毁bean和上下文之前
 * 参考：http://blog.sina.com.cn/s/blog_7d1968e20102x1x4.html
 * @author xuhongyu
 * @date 2019年10月21日
 */
@Component
public class MyContextClosedHandler implements ApplicationListener<ContextClosedEvent>{

	@Autowired
    @Qualifier("globalTaskExecutor")
    private ThreadPoolTaskExecutor globalTaskExecutor;

    private static int WAIT_TIME = 50;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        shutdownAndAwaitTermination(globalTaskExecutor.getThreadPoolExecutor());
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // 启动有序关闭：在该关闭中执行先前提交的任务，但不接受新任务。如果已关闭，则调用没有其他效果
        try {
            // Wait a while for existing tasks to terminate（阻塞，直到所有任务在shutdownrequest之后完成执行，或发生超时，或当前线程中断，以先发生的为准。如果此执行器终止，则为true；如果在终止之前超时，则为false）
            if (!pool.awaitTermination(WAIT_TIME, TimeUnit.SECONDS)) {
            	//若超时，尝试停止所有正在执行的任务，停止正在等待的任务的处理，并返回正在等待执行的任务的列表
                pool.shutdownNow(); 
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(WAIT_TIME, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
