package com.hungnv28.core.controllers;

import com.hungnv28.core.base.BaseController;
import com.hungnv28.core.base.BaseResponse;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.exception.ErrorResponse;
import com.hungnv28.core.services.QuotesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "${sys.api.v1}/quotes")
public class QuotesController extends BaseController {

    private final QuotesService quotesService;


    @GetMapping(value = "/historical/{stockId}")
    public ResponseEntity<BaseResponse> getHistorical(@PathVariable String stockId, @RequestParam String fromDate, @RequestParam String toDate) {
        try {
            return successApi(quotesService.getHistorical(stockId, fromDate, toDate));
        } catch (ApiException exception) {
            log.error("QuotesController_getHistorical: {}", exception.getMessage());
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("QuotesController_getHistorical: {}", exception.getMessage());
            return errorApi(exception.getMessage());
        }
    }
}
