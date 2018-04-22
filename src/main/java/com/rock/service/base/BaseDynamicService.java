package com.rock.service.base;

import com.rock.model.base.CommonPageResult;
import com.rock.model.base.PageQueryRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by rock on 2018/4/12.
 */
public interface BaseDynamicService {

    public void save(Object entity);

    public void update(Object entity);

    public <T> void delete(Class<T> entityClass, Object entityid);

    public <T> void delete(Class<T> entityClass, Object[] entityids);

    /**
     * 查询对象列表，返回List
     * @param nativeSql
     * @param paramMap
     * @return  List<T>
     * @Date    2018年3月15日
     * 更新日志
     * 2018年3月15日  张志朋  首次创建
     *
     */

    <T> List<T> nativeQueryList(String nativeSql, Map<String,Object> paramMap);

    /**
     * 查询对象列表，返回List<Map<key,value>>
     * @param nativeSql
     * @param paramMap
     * @return  List<T>
     * @Date    2018年3月15日
     * 更新日志
     * 2018年3月15日  张志朋  首次创建
     *
     */

    <T> List<T> nativeQueryListMap(String nativeSql,Map<String,Object> paramMap);

    /**
     * 查询对象列表，返回List<组合对象>
     * @param resultClass
     * @param nativeSql
     * @param paramMap
     * @return  List<T>
     * @Date    2018年3月15日
     * 更新日志
     * 2018年3月15日  张志朋  首次创建
     *
     */
    <T> List<T> nativeQueryListModel(Class<T> resultClass, String nativeSql, Map<String,Object> paramMap);


    /**
     * 分页查询：
     * 在nativeQueryListModel基础上增加了总条数数据
     * @param resultClass
     * @param nativeSql
     * @param paramMap
     * @param <T>
     * @return
     */
    <T> CommonPageResult<T> nativePageQueryListModel(Class<T> resultClass, String nativeSql, Map<String,Object> paramMap, PageQueryRequest pageQueryRequest);


}
