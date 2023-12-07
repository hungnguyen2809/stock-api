package com.hungnv28.core.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse {
    protected int status;
    protected Object data;
    protected String code;
    protected boolean error;
    protected String message;
}
