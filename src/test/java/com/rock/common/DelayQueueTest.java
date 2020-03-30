package com.rock.common;

import com.rock.model.Order;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;

/**
 * Created by rock on 2020/3/4.
 */
public class DelayQueueTest {



    @Test
    public void testDelayQueue1() throws InterruptedException {
        DelayQueue<Order> queue = new DelayQueue<>();
        Order order1 = new Order("a", System.currentTimeMillis());
        Order order2 = new Order("b", System.currentTimeMillis() + 5000);
        Order order3 = new Order("c", System.currentTimeMillis() + 4000);
        Order order4 = new Order("d", System.currentTimeMillis() + 6000);
        queue.put(order1);
        queue.put(order2);
        queue.put(order3);
        queue.put(order4);
        System.out.println("begin time:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        for (int i = 0; i < 4; i++) {
            //阻塞方式获取
            Order take = queue.take();
            System.out.format("orderNo:{%s}, fetch time:{%s}\n",take.getOrderNo(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }

}
