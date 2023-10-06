package com.atguigu.ssyx.common.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SsyxException extends RuntimeException {
    private Integer code;

    private SsyxException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}
