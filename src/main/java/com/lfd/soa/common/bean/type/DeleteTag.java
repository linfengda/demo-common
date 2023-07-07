package com.lfd.soa.common.bean.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 逻辑删除枚举 </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-09-26 14:51
 */
@AllArgsConstructor
@Getter
public enum DeleteTag {

    /**
     * 0：正常
     */
    NORMAL(0, "正常"),
    /**
     * 1：删除
     */
    DELETED(1, "删除");

    private final Integer code;
    private final String name;

    public static DeleteTag getType(Integer state) {
        for (DeleteTag deleteTag : values()) {
            if (deleteTag.getCode().equals(state)) {
                return deleteTag;
            }
        }
        return null;
    }
}
