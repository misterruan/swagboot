package com.rock.common.util.jpa;

/**
 * Created by rock on 2018/4/18.
 * 下划线和驼峰规范转换工具类
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description :
 * @Autor : xiongjinpeng  jpx_011@163.com
 * @Date  : 2016年3月18日 上午10:20:47
 * @version :
 */
public class UnderlineToCamelUtil {

    public static final char UNDERLINE='_';

    /**
     * 驼峰转下划线
     * 如：className-> class_name
     * @param param
     * @return
     */
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * 如：class_name-> className
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c==UNDERLINE){
                if (++i<len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
