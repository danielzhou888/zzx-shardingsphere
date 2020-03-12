package com.zzx.sharding.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: 20160219
 * @description: 分页响应
 * @author: zhouzhixiang
 * @date: 2019-12-10
 * @company: 叮当快药科技集团有限公司
 **/
@Data
public class ServiceResponse<T> implements Serializable {

    private static final long serialVersionUID = 6219256383112192228L;

    private int code;
    private String msg;
    private T data;

}
