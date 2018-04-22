package com.rock.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rock.App;
import com.rock.common2.util.JsonUtils;
import com.rock.model.AppConfigBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/3/26.
 */
@Slf4j
public class ForamtTest {



    @Test
    public void testFormat() {
        String pattern ="{0}数据删除失败: 没有找到{1}";
        String format = MessageFormat.format(pattern, "订单", "订单编号");
        log.info("format: "+format);

    }

    @Test
    public void testList2String(){
        List<String> list = new ArrayList<String>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
        String str = StringUtils.collectionToDelimitedString(list, ",");
        log.info(str);
    }

}
