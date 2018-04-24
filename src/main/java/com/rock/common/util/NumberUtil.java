package com.rock.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rock on 2018/1/23.
 */
@Slf4j
public class NumberUtil {

    public static List<Integer> stringToIntegers(String str) {
        List<Integer> list = new LinkedList<>();
        String stringWithComma = formatStringWithComma(str);
        if (StringUtils.isEmpty(stringWithComma)) {
            return list;
        }
        String[] array = stringWithComma.split(",");
        try {
            Arrays.asList(array).stream()
                    .forEach(s->list.add(new Integer(s)));
        } catch (Exception e) {
            log.error( "parse to Integer error" , new Object[] { e });
        }
        return list;
    }

    public static List<String> stringToStrings(String str) {
        List<String> list = new LinkedList<>();
        String stringWithComma = formatStringWithComma(str);
        if (StringUtils.isEmpty(stringWithComma)) {
            return list;
        }
        String[] array = stringWithComma.split(",");
        Arrays.asList(array).stream()
                .forEach(s->list.add(s));
        return list;
    }

    //去除字符串中所有空格，替换中文，等
    private static String formatStringWithComma(String origin){
        if (StringUtils.isEmpty(origin)) {
            return origin;
        }
        String target = origin.replaceAll("\\s*", "");
        target = target.replaceAll("，",",");
        target = target.replaceAll(",,",",");
        return target;
    }



}
