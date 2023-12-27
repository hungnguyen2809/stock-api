package com.hungnv28.core.services;

import com.hungnv28.core.dtos.CoveredWarrantDTO;
import com.hungnv28.core.dtos.StockInfoDTO;

import java.util.List;

public interface StockService {
    List<StockInfoDTO> watchList(long userId) throws Exception;

    boolean addWatchList(long userId, int stockId) throws Exception;

    List<CoveredWarrantDTO> listWarrant(int stockId) throws Exception;
}
