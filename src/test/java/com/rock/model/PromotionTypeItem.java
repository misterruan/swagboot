package com.rock.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 满折类型促销
 */
@Data
public class PromotionTypeItem {
    //满折
    private List<PromotionTypeDiscount> typeDiscountList = new ArrayList<>();


}
