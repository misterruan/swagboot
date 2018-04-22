package com.rock.common.util;

/**
 * Created by rock on 2018/4/21.
 */
public class ObjectUtil {

    /**
     * 方法描述 判断一个对象是否是一个数组
     * @param obj
     * @return
     */
    public static boolean isArray(Object obj) {
        if(obj == null) {
            return false;
        }
        return obj.getClass().isArray();
    }


}
