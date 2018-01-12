package com.rock.controller;

import com.rock.congfig.AppConfigBean;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rock.model.MUser;
import com.rock.service.UserService;

@RestController
@RequestMapping(value = "/usersJpa")
@Api("使用Spring Data JPA的方式查询")
public class MUserController {
	
	@Autowired
	private UserService userService;
    @Autowired
    private AppConfigBean appConfigBean;

    /**
     * 同时支持get、post请求
     * @param name
     * @return
     */
	@RequestMapping(value = "/{name}",  produces = { "application/json" }, method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "使用根据用户名查询用户")
	public MUser findByName(@PathVariable @ApiParam(value = "用户的名称", required = true)String name) {
        MUser byName = userService.findByName(name);
        return byName;
	}


    @RequestMapping(value = "/createUser",  produces = { "application/json" }, method = { RequestMethod.POST })
    @ApiOperation(value = "创建新用户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = MUser.class) })

    public MUser createUser(
            @ApiParam(value = "用户名", required = true) @RequestParam(value = "name", required = true) String name,
            @ApiParam(value = "年龄", required = true) @RequestParam(value = "age", required = true) Integer age,
            @ApiParam(value = "住址", required = true) @RequestParam(value = "address", required = true) String address
    ) {
        MUser mUser = new MUser();
        mUser.setName(name);
        mUser.setAge(age);
        mUser.setAddress(address);
        MUser returnUser = userService.createUser(mUser);
        return returnUser;
    }
	
	
}
