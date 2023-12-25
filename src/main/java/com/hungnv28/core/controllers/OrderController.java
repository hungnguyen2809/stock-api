package com.hungnv28.core.controllers;

import com.hungnv28.core.base.BaseController;
import com.hungnv28.core.base.BaseResponse;
import com.hungnv28.core.controllers.request.OrderCreateRequest;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.exception.ErrorResponse;
import com.hungnv28.core.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "${sys.api.v1}/order")
public class OrderController extends BaseController {

    private final OrderService orderService;

    @GetMapping(value = "/get-order")
    @Operation(summary = "Lấy danh sách đơn hàng theo user")
    public ResponseEntity<BaseResponse> getOrder() {
        try {
            return successApi(orderService.getOrder(getCurrentUser().getUserId()));
        } catch (ApiException exception) {
            log.error("OrderController_getOrder: {}", exception.getMessage());
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("OrderController_getOrder: {}", exception.getMessage());
            return errorApi(exception.getMessage());
        }
    }

    @PostMapping(value = "/add-order")
    @Operation(summary = "Thêm đơn đặt hàng")
    public ResponseEntity<BaseResponse> addOrder(@RequestBody OrderCreateRequest order) {
        try {
            boolean result = orderService.createOrder(getCurrentUser().getUserId(), order);
            if (result) {
                return successApi("OK", "Đặt hàng thành công");
            } else {
                return errorApi("Đặt hàng không thành công");
            }
        } catch (ApiException exception) {
            log.error("OrderController_addOrder: {}", exception.getMessage());
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("OrderController_addOrder: {}", exception.getMessage());
            return errorApi(exception.getMessage());
        }
    }
}
