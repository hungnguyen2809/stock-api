package com.hungnv28.core.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse {
    private int status;
    private Object data;
    private String code;
    private boolean error;
    private String message;
}
