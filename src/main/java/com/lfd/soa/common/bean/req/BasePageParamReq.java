package com.lfd.soa.common.bean.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页req
 *
 * @author linfengda
 * @date 2021-03-08 14:04
 */
@Data
public abstract class BasePageParamReq {
    private static final long serialVersionUID = 3347419754375205054L;
    /**
     * 默认分页大小
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * 默认当前页码
     */
    int DEFAULT_PAGE = 1;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", example = "10")
    private int page = DEFAULT_PAGE;

    /**
     * 分页大小
     */
    @ApiModelProperty(value = "分页大小", example = "1")
    private int pageSize = DEFAULT_PAGE_SIZE;
}
