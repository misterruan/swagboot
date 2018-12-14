package com.rock.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rock.App;
import com.rock.common.util.JsonUtils;
import com.rock.model.AppConfigBean;
import com.rock.model.TestConfigBean;
import com.rock.model.jpa.DsClasses;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SpringValueAnnotationTest {

    @Autowired
    private AppConfigBean appConfigBean;

    @Autowired
    private TestConfigBean testConfigBean;

    @Autowired
    private DsClasses dsClasses;

    @Test
    //测试@Value注解是否正常取值
    public void testGetBeanPoperties() throws JsonProcessingException {
        DsClasses bean = testConfigBean.createBean();
        DsClasses bean1 = testConfigBean.createBean();
        if(bean == bean1){
            log.info("result: same bean ....");
        }else {
            log.info("result: different bean ....");
        }
        System.out.print(JsonUtils.toJsonWithCatchException(appConfigBean));
    }

}
