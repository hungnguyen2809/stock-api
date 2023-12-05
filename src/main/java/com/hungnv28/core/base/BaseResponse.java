package com.hungnv28.core.base;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse implements Serializable {
    protected int status;
    protected Object data;
    protected String code;
    protected boolean error;
    protected String message;
}
