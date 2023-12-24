package com.hungnv28.core.services.impl;

import com.hungnv28.core.daos.StockDAO;
import com.hungnv28.core.dtos.StockInfoDTO;
import com.hungnv28.core.services.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceIml implements StockService {

    private final StockDAO stockDAO;

    @Override
    public List<StockInfoDTO> watchList(long userId) throws Exception {
        return stockDAO.watchList(userId);
    }

    @Override
    public boolean addWatchList(long userId, int stockId) throws Exception {
        return stockDAO.addWatchList(userId, stockId);
    }
}
