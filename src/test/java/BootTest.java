import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hl.App;
import com.hl.congfig.AppConfigBean;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by user on 2017/3/26.
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
//@SpringBootTest(classes=App.class)// 指定spring-boot的启动类
@SpringApplicationConfiguration(classes = App.class)// 1.4.0 前版本
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class BootTest {

    @Autowired
    private AppConfigBean appConfigBean;

    @Test
    //测试@Value注解是否正常取值
    public void testGetBeanPoperties() throws JsonProcessingException {
        System.out.print(new ObjectMapper().writeValueAsString(appConfigBean));
    }

}
