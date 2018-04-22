package com.rock.service.impl;

import com.google.common.base.Strings;
import com.rock.common.exception.ExceptionConstants;
import com.rock.common.exception.SwagBootCommonException;
import com.rock.model.base.CommonPageResult;
import com.rock.model.base.PageQueryRequest;
import com.rock.model.jpa.DsClasses;
import com.rock.model.jpa.DsSubProductBasic;
import com.rock.repository.jpa.one2many.DsClassRepository;
import com.rock.service.base.BaseJpaService;
import org.apache.catalina.Store;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.TokenException;
import org.dom4j.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JpaDsClassesServiceImpl{

	@Autowired
	private DsClassRepository dsClassRepository;


	//简单的分页查询
	public CommonPageResult<DsClasses> pageQueryFindAll(PageQueryRequest request){
        CommonPageResult<DsClasses> commonPageResult = new CommonPageResult<DsClasses>();
        Page<DsClasses> dsClassesPage = dsClassRepository.findAll(buildPageableWithSort(request));
        commonPageResult.setData(dsClassesPage.getContent());
        commonPageResult.setTotalSize(commonPageResult.getTotalSize());
        commonPageResult.setCode(ExceptionConstants.success.getCode());
        commonPageResult.setInfo(ExceptionConstants.success.getInfo());
        return commonPageResult;
    }

    public DsClasses findByClassName(String className) {
        List<DsClasses> byClassName = dsClassRepository.findByClassName(className);
        return CollectionUtils.isEmpty(byClassName) ? null : byClassName.get(0);
    }

    public DsClasses findByClassId(Long Id) {
	    throw new NullPointerException("test");
//        return dsClassRepository.findOne(Id);
    }

    public List<DsClasses> findByClassNameLike(String className) {
//        Token token = new Token(1,"ss",2,3);
//	    throw new TokenException("test token",token);
        throw new SwagBootCommonException(ExceptionConstants.errer1001.getCode(),ExceptionConstants.errer1001.getInfo());
//        return dsClassRepository.findByClassNameLike(className);
    }


    public DsClasses create(DsClasses dsClasses){
        return dsClassRepository.save(dsClasses);
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



}
