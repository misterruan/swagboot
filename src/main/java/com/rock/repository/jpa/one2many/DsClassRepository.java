package com.rock.repository.jpa.one2many;

import com.rock.model.jpa.DsClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DsClassRepository extends JpaRepository<DsClasses, Long> , JpaSpecificationExecutor<DsClasses> {

    //如果返回类型定义为DsClasses，那么结果集如果大于1条会报错
    //javax.persistence.NonUniqueResultException: result returns more than one elements
    List<DsClasses> findByClassName(String className);

    List<DsClasses> findByClassNameLike(String className);

}