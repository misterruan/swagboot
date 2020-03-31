package com.rock.service.common;

import com.rock.common.util.JsonUtils;
import com.rock.model.queue.NotifyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rock on 2020/3/16.
 * 用于例子导入成功后异步通知，主要因为例子创建是异步的
 * 所以需要延迟查询例子信息
 */
@Component
@Slf4j
public class MktGenerateNotifyDelayQueue {

    /**
     * 延迟队列
     */
    private static DelayQueue<NotifyContext> delayQueue = new DelayQueue<>();

    private static final ExecutorService exec = Executors.newFixedThreadPool(4);


    /**
     * 处理DelayQueue中需要处理的数据
     */
    @PostConstruct
    public void handle() {
        //固定线程阻塞获取消息，逻辑执行交给线程池并发处理
        new Thread(() -> {
            log.info("[MktGenerateNotifyDelayQueue]消费者线程启动成功");
            while (true) {
                try {
                    //阻塞方式获取
                    NotifyContext context = delayQueue.take();
                    exec.execute(() -> {
//                        notifyBiz.process(context);
                        log.info("handled DelayQueue message:{}", JsonUtils.toJsonWithCatchException(context));
                    });
                } catch (InterruptedException e) {
                    log.error("从MktGenerateNotifyDelayQueue阻塞获取数据出现异常", e);
                }
            }
        }, "MktGenerateNotifyDelayQueue-thread").start();
    }



    /**
     * 存入队列
     *
     * @param context
     */
    public static void putIntoQueue(NotifyContext context) {
        if (Objects.isNull(context)
                || context.getActiveTime() <= 0
                || Objects.isNull(context.getDetailBeanId())) {
            return;
        }
        delayQueue.put(context);
    }

}
