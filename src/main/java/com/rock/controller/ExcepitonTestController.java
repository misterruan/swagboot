package com.rock.controller;

import com.rock.common.exception.ExceptionConstants;
import com.rock.common.exception.SwagBootCommonException;
import com.rock.model.base.CommonResult;
import com.rock.model.jpa.DsProductPageQueryRequest;
import com.rock.model.test.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

/**
 * 用于测试异常处理体系的范例
 * Created by rock on 2019/8/9.
 */
@RestController
@RequestMapping(value = "/test/global-exception-handle")
@Api("Redis操作测试controller：JPA/JOOQ")
@Slf4j
public class ExcepitonTestController {


    /***
     * 测试全局异常处理中的业务异常处理
     * 以及MessageFormat的使用
     * @return
     */
    @RequestMapping(value = "/biz-exception", produces = {"application/json"}, method = {RequestMethod.GET})
    @ApiOperation(value = "测试业务异常")
    public CommonResult<String> test1() {
        if (true)
            throw new SwagBootCommonException(ExceptionConstants.errer10000.getCode(), MessageFormat.format(ExceptionConstants.errer10000.getInfo(), "添加数据", "权限不足"));
        return CommonResult.ok(null);
    }

    @RequestMapping(value = "/validated-exception", produces = {"application/json"}, method = {RequestMethod.POST})
    @ApiOperation(value = "测试参数校验异常")
    public CommonResult<String> test2(@Validated @ModelAttribute Student student) {
        return CommonResult.ok(null);
    }

    @RequestMapping(value = "/unexpected-exception", produces = {"application/json"}, method = {RequestMethod.GET})
    @ApiOperation(value = "测试非预期全局异常")
    public CommonResult<String> test3() {
        int i = 1 / 0;
        return CommonResult.ok(null);
    }
}
