package com.rock.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rock.App;
import com.rock.common2.util.JsonUtils;
import com.rock.model.AppConfigBean;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by user on 2017/3/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
//@ActiveProfiles("sit")

public class SpringValueAnnotationTest {

    @Autowired
    private AppConfigBean appConfigBean;

    @Test
    //测试@Value注解是否正常取值
    public void testGetBeanPoperties() throws JsonProcessingException {

        System.out.print(JsonUtils.toJsonWithCatchException(appConfigBean));
    }

}