package com.lfd.soa.common.bean.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: linfengda
 * @date: 2021-09-02 20:49
 */
@ApiModel
public class Resp<T> {
    private static final String MSG_SUCCESS = "SUCCESS";
    private static final String MSG_ERROR = "ERROR";
    @ApiModelProperty("业务代码")
    private Integer code;
    @ApiModelProperty("业务数据")
    private T data;
    @ApiModelProperty("业务成功标记")
    private Boolean isSuccess;
    @ApiModelProperty("业务消息")
    private String message;
    @ApiModelProperty("响应时间戳")
    private Date timestamp;
    @ApiModelProperty("错误详情")
    private Map<String, List<String>> errors;

    public Resp() {
        this.timestamp = new Date();
        this.errors = new HashMap<>(16);
        this.code = 0;
        this.isSuccess = true;
        this.message = "SUCCESS";
    }

    public Resp(T data) {
        this();
        this.data = data;
    }

    public Resp(T data, String message) {
        this();
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public Boolean getIsSuccess() {
        return this.isSuccess;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public Map<String, List<String>> getErrors() {
        return this.errors;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}
