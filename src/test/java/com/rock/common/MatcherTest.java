package com.rock.common;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rock on 2018/4/19.
 */
public class MatcherTest {



@Test
    public void test() {
        //SELECT 列名称（*所有列） FROM 表名称

        //SELECT 列名称 FROM 表名称 where 条件
    String sql ="SELECT\n" +
            "                \"s.id skuId,s.sub1,s.sub2,p.id spuId,p.spu,p.images,p.detail_images,\\n\" +\n" +
            "                \"p.sub1_name,p.sub2_name,c.id classId,c.class_name\\n\" +\n" +
            "                \"FROM\\n\" +\n" +
            "                \"test.ds_sub_product_basic s\\n\" +\n" +
            "                \"left join test.ds_product_basic p on s.product_id = p.id\\n\" +\n" +
            "                \"left join test.ds_classes c on p.class_id = c.id\\n\" +\n" +
            "                \"where 1=1";
        System.out.println( matchSql( "select * from aaa ") );
        System.out.println( matchSql( "select id,name,password from bbb where id = 1 ") );
        //INSERT INTO 表名称 VALUES (值1, 值2,....)
        //INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
        System.out.println( matchSql("insert into ccc valuse(1,'neo','password')") );
        System.out.println( matchSql("insert into ddd (id,name,password) valuses(1,'neo','password')") );
        //UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
        System.out.println( matchSql("update eee set name = 'neo' where id = 1 ") );
        //DELETE FROM 表名称 WHERE 列名称 = 值
        System.out.println( matchSql("delete from fff where id = 1 ") );

        String sql2 = "delete from fff where id = 1 ";
    }


    public static String matchSql(String sql){
        Matcher matcher = null;
        //SELECT 列名称 FROM 表名称

        //SELECT * FROM 表名称
        sql = sql.toLowerCase();
        if( sql.startsWith("select") ){

            matcher = Pattern.compile("select(\\s+.*)from.*").matcher(sql);
//            matcher = Pattern.compile("select\\s.+from\\s(.+)where\\s(.*)").matcher(sql);
            if(matcher.find()){
                return matcher.group(1);
            }
        }
        //INSERT INTO 表名称 VALUES (值1, 值2,....)
        //INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
        if( sql.startsWith("insert") ){
            matcher = Pattern.compile("insert\\sinto\\s(.+)\\(.*\\)\\s.*").matcher(sql);
            if(matcher.find()){
                return matcher.group(1);
            }
        }
        //UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
        if( sql.startsWith("update") ){
            matcher = Pattern.compile("update\\s(.+)set\\s.*").matcher(sql);
            if(matcher.find()){
                return matcher.group(1);
            }
        }
        //DELETE FROM 表名称 WHERE 列名称 = 值
        if( sql.startsWith("delete") ){
            matcher = Pattern.compile("delete\\sfrom\\s(.+)where\\s(.*)").matcher(sql);
            if(matcher.find()){
                return matcher.group(1);
            }
        }
        return null;
    }



    private int getStartFromIndex(String sql){
        sql = sql.toLowerCase();
        int startFromIndex = sql.indexOf("from");
        if(startFromIndex > -1){
            char c1 = sql.charAt(startFromIndex - 1);
            char c2 = sql.charAt(startFromIndex + 5);
            //c1和c2都不是数字或者字母,才说明找到了select..from..中的from
            if((Character.isLetter(c1) || Character.isDigit(c1)) || (Character.isLetter(c2) || Character.isDigit(c2))){
                //说明没有找到，把不是我们需要的from替换成其他字符，继续寻找
                sql = sql.replaceFirst("from", "aaaa");
                startFromIndex = getStartFromIndex(sql);
            }
        }
        return startFromIndex;
    }





}
