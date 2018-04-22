package com.rock.service.base;


import com.rock.common.exception.SwagBootCommonException;
import com.rock.common.exception.ExceptionConstants;
import com.rock.model.base.PageQueryRequest;
import com.rock.model.jpa.AbstractEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.text.MessageFormat;

/**
 * Spring Data JPA DB操作抽象基类
 * Created by huangdong on 2017/12/3.
 */
public abstract class BaseJpaService<T extends AbstractEntity> {


    /**
     * 取得数据仓库对象
     * @return
     */
    public abstract JpaRepository<T, Long> getRepository();

    /**
     * 创建查询规格(如果使用复杂的动态关联查询，子类必须要覆盖重写该方法)
     * @return
     */
    public abstract Specification<T> makeSpecification(PageQueryRequest request);

    //插入前的检查(有效性检查)
    public void beforeCreate(T bean) throws SwagBootCommonException { }

    //更新前的检查(有效性检查报警, 同步更新)
    public void beforeUpdate(T bean) throws SwagBootCommonException{ }

    //删除前的检查(有效性检查报警, 关联删除)
    public void beforeDelete(T bean) throws SwagBootCommonException { }


    /**
     * 复杂动态分页条件查询
     * @param request
     * @return
     */
    public Page<T> query(PageQueryRequest request) {
        //构建分页对象
        Pageable pageable = buildPageableWithSort(request);

        Page<T> result = transRepository().findAll(makeSpecification(request), pageable);

        return result;
    }


    /**
     * 构建分页查询对象，包含了排序字段
     * @param request
     * @return
     */
    public Pageable buildPageableWithSort(PageQueryRequest request){
        Sort.Direction direction = Sort.Direction.DESC;
        String sortField = "id";
        if(StringUtils.isNotEmpty(request.getSort()) && request.getSort().split(" ").length == 2){
            String[] sorts = request.getSort().split(" ");
            if("asc".equals(sorts[1]) || "ASC".equals(sorts[1])){
                direction = Sort.Direction.ASC;
            }
            sortField = sorts[0];
        }
        Sort sort = new Sort(direction,sortField);
        return new PageRequest(request.getPage(),request.getSize(),sort);
    }



    /**
     * 新增
     * @param bean
     * @return
     */
    @Transactional
    public T create(T bean)  {
        if(ObjectUtils.isEmpty(bean)) {
            throw new SwagBootCommonException(ExceptionConstants.errer10005.getCode(), ExceptionConstants.errer10005.getInfo());        }

        beforeCreate(bean);

        return getRepository().save(bean);
    }

    /**
     * 更新
     * @param bean
     * @return
     */
    @Transactional
    public T update(T bean)  {
        if(ObjectUtils.isEmpty(bean.getId())) {
            throw new SwagBootCommonException(ExceptionConstants.errer10005.getCode(), ExceptionConstants.errer10005.getInfo());
        }

        //由于 version 存在, 必须先取出原记录, 直接 save 则会执行 sql insert
        T last = getRepository().findOne(bean.getId());

        if(ObjectUtils.isEmpty(last)) {
            throw new SwagBootCommonException(ExceptionConstants.errer10002.getCode(),
                    MessageFormat.format(ExceptionConstants.errer10005.getInfo(),"id ="+bean.getId()));
        }

        T updateBean = null;

        if(bean.isUpdateSelective()) {
            //修改对象中非null值的部分
            updateBean = last;
            BeanUtils.copyProperties(bean, updateBean, com.rock.common.util.BeanUtils.getNullPropertyNames(bean));

        }else{
            //按照给定内容进行修改
            updateBean = bean;

            //由于 version 存在, 必须先取出原记录, 直接 save 则会执行 sql insert
            //设置乐观锁
            updateBean.setVersion(last.getVersion());
        }

        beforeUpdate(updateBean);

        T result = getRepository().save(updateBean);
        return result;
    }

    /**
     * 删除
     * @param id
     * @return 被删除的对象信息
     */
    @Transactional
    public T delete(Long id)  {
        T bean = getRepository().findOne(id);

        if(ObjectUtils.isEmpty(bean)) {
            throw new SwagBootCommonException(ExceptionConstants.errer10002.getCode(),
                    MessageFormat.format(ExceptionConstants.errer10005.getInfo(),"id ="+bean.getId()));
        }

        beforeDelete(bean);

        getRepository().delete(id);
        return bean;
    }



    private JpaSpecificationExecutor<T> transRepository() {
        if(getRepository() instanceof JpaSpecificationExecutor) {
            return (JpaSpecificationExecutor<T>)getRepository();
        }
        return null;
    }

}
