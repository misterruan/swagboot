package com.rock.model.queue;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by rock on 2020/3/13.
 */
@Data
public class NotifyContext implements Delayed,Serializable {

    /**
     * 需要发送通知的记录id
     */
    private Long detailBeanId;

    /**
     * 触发时间（毫秒）
     */
    private long activeTime;


    /**
     * getDelay定义了剩余到期时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(activeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
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
        if (other instanceof NotifyContext) {
            NotifyContext otherRequest = (NotifyContext)other;
            long otherStartTime = otherRequest.getActiveTime();
            return (int)(this.activeTime - otherStartTime);
        }
        return 0;
    }

}
