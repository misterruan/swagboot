package com.rock.controller;

import com.alibaba.fastjson.JSONObject;
import com.rock.common.aspect.SaveActionLog;
import com.rock.common.util.JsonUtils;
import com.rock.model.base.CommonPageResult;
import com.rock.model.base.CommonResult;
import com.rock.model.base.PageQueryRequest;
import com.rock.model.jpa.DsClasses;
import com.rock.model.jpa.DsProductPageQueryRequest;
import com.rock.model.jpa.DsProductVo;
import com.rock.service.base.BaseDynamicService;
import com.rock.service.impl.JpaDsClassesServiceImpl;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
@Api("一对多商品范例：JPA/JOOQ")
@Slf4j
public class JpaDsProductController {
	
	@Autowired
	private JpaDsClassesServiceImpl jpaDsClassesService;
	@Autowired
    private BaseDynamicService baseDynamicService;



    /**
     * restful风格路由
     * @param classId
     * @return
     */
    @RequestMapping(value = "/{classId}",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "使用商品分类精确查询")
    public CommonResult<DsClasses> findByClassId(@PathVariable @ApiParam(value = "分类Id", required = true)Long classId) {


        CommonResult<DsClasses> commonResult = new CommonResult<>();
        commonResult.setData(jpaDsClassesService.findByClassId(classId));
        return commonResult;
    }

    /**
     * 同时支持get、post请求
     * @param name
     * @return
     */
	@RequestMapping(value = "/findByName",  produces = { "application/json" }, method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "使用商品分类精确查询")
	public CommonResult<DsClasses> findByName(@ApiParam(value = "分类的名称", required = true) @RequestParam(value = "name", required = true) String name) {
        CommonResult<DsClasses> commonResult = new CommonResult<>();
        commonResult.setData(jpaDsClassesService.findByClassName(name));
        return commonResult;
	}

    /**
     * JPA的模糊查询
     * @param name
     * @return
     */
    @RequestMapping(value = "/findByNameLike",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "使用商品分类模糊查询")
    public CommonResult<List<DsClasses>> findByNameLike(@ApiParam(value = "分类的名称需要带上%,如：%女装% ", required = true) @RequestParam(value = "name", required = true) String name) {
        CommonResult<List<DsClasses>> commonResult = new CommonResult<List<DsClasses>>();
        commonResult.setData(jpaDsClassesService.findByClassNameLike(name));
        return commonResult;
    }

    @SaveActionLog(desc = "创建新分类",moduleId = "分类")
    @RequestMapping(value = "/createClass",  produces = { "application/json" }, method = { RequestMethod.POST })
    @ApiOperation(value = "创建新分类")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = DsClasses.class) })
    public CommonResult<DsClasses> createClass(@ApiParam(value = "分类", required = true) @RequestBody DsClasses dsClasses) {
        CommonResult<DsClasses> commonResult = new CommonResult<>();
        commonResult.setData(jpaDsClassesService.create(dsClasses));
        return commonResult;
    }

    /**
     * 多表关联，自定义结果集的复杂分页查询
     * @param pageQueryRequest
     * @return
     */
    @ApiOperation(value = "商品大对象分页查询接口", notes = "支持数据筛选、排序、分页", httpMethod = "GET")
    @RequestMapping(value = "/pageQuery", method = RequestMethod.GET)
    public CommonPageResult<DsProductVo> query(@Validated @ModelAttribute DsProductPageQueryRequest pageQueryRequest) {
        String action = "商品大对象分页查询接口";
        log.info("执行 {}, 请求数据: {}", action, JSONObject.toJSONString(pageQueryRequest));
        HashMap<String, Object> paramsMap = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT\n" +
                "s.id skuId,s.sub1,s.sub2,p.id spuId,p.spu,p.images,p.detail_images,\n" +
                "p.sub1_name,p.sub2_name,c.id classId,c.class_name\n" +
                "FROM\n" +
                "test.ds_sub_product_basic s\n" +
                "left join test.ds_product_basic p on s.product_id = p.id\n" +
                "left join test.ds_classes c on p.class_id = c.id\n" +
                "where 1=1");
        if(StringUtils.isNotEmpty(pageQueryRequest.getClassName())){
            paramsMap.put("className",pageQueryRequest.getClassName());
            builder.append(" and c.class_name=:className");
        }
        if(StringUtils.isNotEmpty(pageQueryRequest.getSpu())){
            builder.append(" and p.spu like :spu");
            //like 写法
            paramsMap.put("spu","%"+pageQueryRequest.getSpu()+"%");
        }
        if(StringUtils.isNotEmpty(pageQueryRequest.getSort())){
            builder.append(" order by "+pageQueryRequest.getSort());
            //采用参数法order by 'skuId desc' limit 0, 10 ,有'号，有问题,所以采用直接拼接的写法
//            builder.append(" order by :sort");
//            paramsMap.put("sort",pageQueryRequest.getSort());
        }
        String sql = builder.toString();
        CommonPageResult<DsProductVo> result = baseDynamicService.nativePageQueryListModel(DsProductVo.class, sql, paramsMap,pageQueryRequest);
        return result;
    }
	
	
}
