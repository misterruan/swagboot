package com.rock.common;

import com.rock.common.util.ValidUtils;
import com.rock.model.PromotionTypeDiscount;
import org.junit.Test;

/**
 * Created by rock on 2018/4/22.
 */
public class BeanValidateTest {


    @Test
    public void validteTest(){
        PromotionTypeDiscount discount = new PromotionTypeDiscount();
        discount.setLowerLimit(20);
        discount.setUpperLimit(30);
        discount.setLowerLimitSymbol("<=");
        discount.setUpperLimitSymbol(">=");
        discount.setDiscountRate(80);
        ValidUtils.validBean(discount);
    }

}
