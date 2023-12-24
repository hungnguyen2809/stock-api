package com.hungnv28.core.controllers.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockAddWatchRequest implements Serializable {
    private int stockId;
}
