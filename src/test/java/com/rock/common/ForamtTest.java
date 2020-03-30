package com.rock.common;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/3/26.
 */
@Slf4j
public class ForamtTest {


    public <T> void printMsg( List<T> a){
        Class<? extends List> aClass = a.getClass();
        new Object();
    }

    @Test
    public void testFormat() {
       printMsg(Lists.newArrayList("a","B"));
        String pattern ="{0}数据删除失败: 没有找到{1}";
        String format = MessageFormat.format(pattern, "订单", "订单编号");
        log.info("format: "+format);

    }

    @Test
    public void testList2String(){
        List<String> list = new ArrayList<String>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
        String str = StringUtils.collectionToDelimitedString(list, ",");
        log.info(str);
        BigDecimal bigDecimal = new BigDecimal("12.134");
        BigDecimal bigDecimal2 = new BigDecimal("12.4");
        BigDecimal bigDecimal3 = new BigDecimal("12.50");
        BigDecimal a = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal b = bigDecimal2.setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal c = bigDecimal3.setScale(4, BigDecimal.ROUND_HALF_UP);
        new Object();
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);

    }

}
