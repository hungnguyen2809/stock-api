package com.hungnv28.core.daos;

import com.hungnv28.core.dtos.StockInfoDTO;

import java.util.List;

public interface StockDAO {
    List<StockInfoDTO> watchList(long userId) throws Exception;

    boolean addWatchList(long userId, int stockId) throws Exception;
}
