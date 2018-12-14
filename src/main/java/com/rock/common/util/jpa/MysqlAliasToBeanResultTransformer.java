package com.rock.common.util.jpa;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by rock on 2018/4/16.
 * 自定义的Mysql属性值到普通POJO映射的转换器,用于Jpa的nativeSql的模式
 * 主要用于复杂的关联查询，自定义结果集的情况
 * 转换范例：mysql->POJO ：class_name->className
 */
public class MysqlAliasToBeanResultTransformer extends AliasedTupleSubsetResultTransformer {

    private final Class resultClass;
    private boolean isInitialized;
    private String[] aliases;
    private Setter[] setters;

    public MysqlAliasToBeanResultTransformer(Class resultClass) {
        Objects.requireNonNull(resultClass, "resultClass cannot be null");
        this.isInitialized = false;
        this.resultClass = resultClass;

    }

    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    public Object transformTuple(Object[] tuple, String[] aliases) {
        try {
            if(!this.isInitialized) {
                this.initialize(aliases);
            } else {
                this.check(aliases);
            }

            Object result = this.resultClass.newInstance();

            for(int e = 0; e < aliases.length; ++e) {
                if(this.setters[e] != null) {
                    //自己改造开始,mysql 类型转成set参数类型
                    tuple[e] = mysqlNumToJavaNum(tuple[e],this.setters[e]);
                    //自己改造结束
                    this.setters[e].set(result, tuple[e], (SessionFactoryImplementor)null);
                }
            }

            return result;
        } catch (InstantiationException var5) {
            throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
        } catch (IllegalAccessException var6) {
            throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
        }
    }

    private void initialize(String[] aliases) {
        PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(new PropertyAccessStrategy[]{PropertyAccessStrategyBasicImpl.INSTANCE, PropertyAccessStrategyFieldImpl.INSTANCE, PropertyAccessStrategyMapImpl.INSTANCE});
        this.aliases = new String[aliases.length];
        this.setters = new Setter[aliases.length];

        for(int i = 0; i < aliases.length; ++i) {
            String alias = aliases[i];
            //自己改造开始,针对mysql，将下划线方式命名改成驼峰式，进行匹配
            alias = UnderlineToCamelUtil.underlineToCamel(alias);
            //自己改造结束
            if(alias != null) {
                this.aliases[i] = alias;
                this.setters[i] = propertyAccessStrategy.buildPropertyAccess(this.resultClass, alias).getSetter();
            }
        }

        this.isInitialized = true;
    }

    private void check(String[] aliases) {
        if(!aliasEquals(aliases, this.aliases)) {
            throw new IllegalStateException("aliases are different from what is cached; aliases=" + Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
        }
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            MysqlAliasToBeanResultTransformer that = (MysqlAliasToBeanResultTransformer)o;
            return !this.resultClass.equals(that.resultClass)?false:Arrays.equals(this.aliases, that.aliases);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.resultClass.hashCode();
        result = 31 * result + (this.aliases != null?Arrays.hashCode(this.aliases):0);
        return result;
    }

    /**
     * 自定义，以后有新的类型转换可以自己添加
     * 将结果集中mysql映射出的java.math.BigInteger或者java.lang.Integer，转换成model中set方法参数类型
     * @param mysqlNum mysql对应的类型
     * @param setter 当前model属性对应的set方法
     * @return
     */
    private Object mysqlNumToJavaNum(Object mysqlNum,Setter setter){
        Object javaNum = mysqlNum;
        Class<?> paramterClass = setter.getMethod().getParameterTypes()[0];
        if(mysqlNum != null){
            if("java.lang.Integer".equals(paramterClass.getName())){
                javaNum = new Integer(mysqlNum.toString());
            }
            if("java.lang.Long".equals(paramterClass.getName())){
                javaNum = new Long(mysqlNum.toString());
            }
            if("java.lang.Float".equals(paramterClass.getName())){
                javaNum = new Long(mysqlNum.toString());
            }
            if("java.lang.Double".equals(paramterClass.getName())){
                javaNum = new Long(mysqlNum.toString());
            }
        }
        return javaNum;
    }

    /**
     * 自定义的属性名检查，兼容下划线式和驼峰式
     * @param a:下划线或者其他方式命名的数组
     * @param a2:驼峰式命名的数组
     * @return
     */
    private static boolean aliasEquals(Object[] a, Object[] a2) {
        if (a==a2)
            return true;
        if (a==null || a2==null)
            return false;

        int length = a.length;
        if (a2.length != length)
            return false;

        for (int i=0; i<length; i++) {
            Object o1 = a[i];
            Object o2 = a2[i];
            String camelO1 = null;
            if(o1 != null){
                camelO1 = UnderlineToCamelUtil.underlineToCamel(o1.toString());
            }
            if (!(o1==null ? o2==null : (o1.equals(o2) || camelO1.equals(o2))))
                return false;
        }
        return true;
    }
}
