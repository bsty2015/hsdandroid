package com.jc.product;

/**
 * Created by lrh on 30/8/15.
 */
public enum ProductStatus {

    销售中("0"),待上架("1"), 售完("2"),兑付中("3"),已兑付("4"),新建("5"),撤消("6");

    private final String code;

    private ProductStatus(String code){
        this.code = code;
    }
}
