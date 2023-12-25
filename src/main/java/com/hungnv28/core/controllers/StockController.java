package com.hungnv28.core.controllers;

import com.hungnv28.core.base.BaseController;
import com.hungnv28.core.base.BaseResponse;
import com.hungnv28.core.controllers.request.StockAddWatchRequest;
import com.hungnv28.core.dtos.StockInfoDTO;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.exception.ErrorResponse;
import com.hungnv28.core.services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "${sys.api.v1}/stock")
public class StockController extends BaseController {

    private final StockService stockService;

    @GetMapping(value = "/watch-list")
    @Operation(summary = "Lấy danh sách theo dõi chứng khoán theo user")
    public ResponseEntity<BaseResponse> watchList() {
        try {
            List<StockInfoDTO> lisStock = stockService.watchList(getCurrentUser().getUserId());
            return successApi(lisStock);
        } catch (ApiException exception) {
            log.error("StockController_watchList: {}", exception.getMessage());
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("StockController_watchList: {}", exception.getMessage());
            return errorApi(exception.getMessage());
        }
    }

    @PostMapping(value = "/add-watch")
    @Operation(summary = "Thêm chứng khoán vào danh sách xem")
    public ResponseEntity<BaseResponse> addWatch(@RequestBody StockAddWatchRequest watchListRequest) {
        try {
            boolean result = stockService.addWatchList(getCurrentUser().getUserId(), watchListRequest.getStockId());
            if (result) {
                return successApi(true, "Thêm thành công");
            } else {
                return errorApi("Thêm không thành công");
            }
        } catch (ApiException exception) {
            log.error("StockController_addWatch: {}", exception.getMessage());
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("StockController_addWatch: {}", exception.getMessage());
            return errorApi(exception.getMessage());
        }
    }
}
