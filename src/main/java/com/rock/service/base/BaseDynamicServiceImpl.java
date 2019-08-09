package com.rock.service.base;

import com.rock.common.exception.ExceptionConstants;
import com.rock.common.exception.SwagBootCommonException;
import com.rock.common.util.ObjectUtil;
import com.rock.common.util.SwagBootStringUtil;
import com.rock.common.util.jpa.MysqlAliasToBeanResultTransformer;
import com.rock.model.base.CommonPageResult;
import com.rock.model.base.PageQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * Created by rock on 2018/4/12.
 * 动态jpql/nativesql查询的实现类
 */
@Slf4j
@Repository
public class BaseDynamicServiceImpl implements BaseDynamicService {

    //查一下@PersistenceContext使用方式
    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void save(Object entity) {
        em.persist(entity);
    }

    @Override
    public void update(Object entity) {
        em.merge(entity);
    }

    @Override
    public <T> void delete(Class<T> entityClass, Object entityid) {
        delete(entityClass, new Object[] { entityid });
    }

    @Override
    public <T> void delete(Class<T> entityClass, Object[] entityids) {
        for (Object id : entityids) {
            em.remove(em.getReference(entityClass, id));
        }
    }

    @Override
    public <T> List<T> nativeQueryList(String nativeSql, Map<String,Object> paramMap) {
        Query q = createNativeQuery(nativeSql, paramMap);
        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.TO_LIST);
        return q.getResultList();
    }

    @Override
    public <T> List<T> nativeQueryListModel(Class<T> resultClass, String nativeSql, Map<String,Object> paramMap) {
        Query q = createNativeQuery(nativeSql, paramMap);;
//        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(resultClass));
        //改成自定义的Mysql数据库字段到POJO属性的转换器
        q.unwrap(SQLQuery.class).setResultTransformer(new MysqlAliasToBeanResultTransformer(resultClass));
        return q.getResultList();
    }


    //分页查询
    @Override
    public <T> CommonPageResult<T> nativePageQueryListModel(Class<T> resultClass, String nativeSql, Map<String, Object> paramMap, PageQueryRequest pageQueryRequest) {
        CommonPageResult<T> commonPageResult = new CommonPageResult<>();
        Query q = createNativeQuery(nativeSql, paramMap);
        q.setMaxResults(pageQueryRequest.getSize());//每页条数
        q.setFirstResult(pageQueryRequest.getPage() * pageQueryRequest.getSize());//开始行号,0开始
        q.unwrap(SQLQuery.class).setResultTransformer(new MysqlAliasToBeanResultTransformer(resultClass));
        //列表内容
        commonPageResult.setData(q.getResultList());
        Query countQ = createNativeQuery(querySqlToCountSql(nativeSql), paramMap);
        Object singleResult = countQ.getSingleResult();
        if(ObjectUtil.isArray(singleResult)){
            Object[] singleResultArray = (Object[]) singleResult;
            if (singleResultArray.length > 0){
                //总条数
                commonPageResult.setTotalSize(new Long(singleResultArray[0].toString()));
            }
        }
        commonPageResult.setPage(pageQueryRequest.getPage());
        commonPageResult.setSize(pageQueryRequest.getSize());
        return commonPageResult;
    }

    @Override
    public <T> List<T> nativeQueryListMap(String nativeSql, Map<String,Object> paramMap) {
        Query q = createNativeQuery(nativeSql, paramMap);
        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return q.getResultList();
    }


    private Query createNativeQuery(String sql, Map<String,Object> paramMap) {
        Query q = em.createNativeQuery(sql);
        //遍历map
        for (String key : paramMap.keySet()) {
            q.setParameter(key,paramMap.get(key));
        }
        return q;
    }


    /**
     * 把普通查询sql转成count(*) 统计条数的sql，用于分页总条数显示
     * @param querySql
     * @return
     * 备注：如果替换掉select 和from之间的所有字段，如果有排序等会出问题，所以
    采用改造最少的方式SELECT count(*) ,s.id skuId FROM 直接插入count(*)
     */
    private String querySqlToCountSql(String querySql){
        if(StringUtils.isNotEmpty(querySql)){
            querySql = querySql.toLowerCase();
            int endSelectIndex = querySql.indexOf("select") + 6;
            StringBuilder sb = new StringBuilder();
            sb.append(querySql).insert(endSelectIndex, SwagBootStringUtil.getBlankSpace()+"count(*),"+SwagBootStringUtil.getBlankSpace());
            return sb.toString();
        }else {
            throw new SwagBootCommonException(ExceptionConstants.errer10001.getCode(),ExceptionConstants.errer10001.getInfo());
        }

    }


}
