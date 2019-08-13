package com.rock.common.exception;

/**
 * Created by dingyuanjun on 17/2/28.
 */
public class ExceptionConstants {

    /**
     * 使用范例
     * String pattern ="{0}数据删除失败: 没有找到{1}";
     String format = MessageFormat.format(pattern, "订单", "订单编号");
     */
    //全局类
    public static final Constant success = new Constant(0,"OK");
    public static final Constant errer1000 = new Constant(1000,"服务端错误");
    public static final Constant errer1001 = new Constant(1001,"Token解析错误");
    public static final Constant errer1002 = new Constant(1002,"重复的请求");



    //业务类
    public static final Constant errer10000 = new Constant(10000,"{0}失败: {1}");
    public static final Constant errer10001 = new Constant(10001,"执行失败: 数据访问异常");
    public static final Constant errer10002 = new Constant(10002,"数据更新失败: 没有找到要更新的{0}");
    public static final Constant errer10003 = new Constant(10003,"数据删除失败: 该{0}与{1}存在关联");
    public static final Constant errer10004 = new Constant(10004,"{0} 执行失败: 参数 {1} 有误, 原因: {2}");
    public static final Constant errer10005 = new Constant(10005,"参数错误");
    public static final Constant errer10006 = new Constant(10006,"数据库执行异常");
    public static final Constant errer10007 = new Constant(10007,"文件上传错误");






}
