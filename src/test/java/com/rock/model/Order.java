package com.rock.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by rock on 2020/3/4.
 */
@Data
@AllArgsConstructor
public class Order implements Delayed,Serializable {

    private String orderNo;//订单号

    private long startTime; // 超时时间毫秒

    /**
     * getDelay定义了剩余到期时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * compareTo方法定义了元素排序规则，注意，元素的排序规则影响了元素的获取顺序
     * @param other
     * @return
     */
    @Override
    public int compareTo(Delayed other) {
        if (other == this) {
            return 0;
        }
        if (other instanceof Order) {
            Order otherRequest = (Order)other;
            long otherStartTime = otherRequest.getStartTime();
            return (int)(this.startTime - otherStartTime);
        }
        return 0;
    }
}
