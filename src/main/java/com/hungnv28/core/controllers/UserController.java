package com.hungnv28.core.controllers;

import com.hungnv28.core.base.BaseController;
import com.hungnv28.core.base.BaseResponse;
import com.hungnv28.core.dtos.UserInfoDTO;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.exception.ErrorResponse;
import com.hungnv28.core.services.UserService;
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
@RequestMapping(value = "${sys.api.v1}/user")
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping(value = "/{userId}")
    @Operation(summary = "Lấy thông tin người dùng")
    public ResponseEntity<BaseResponse> getUserById(@PathVariable String userId) {
        try {
            UserInfoDTO users = userService.findUserById(userId, getCurrentUser());
            return successApi(users);
        } catch (ApiException exception) {
            log.error("UserController_getUserById: {}", exception.getMessage());
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("UserController_getUserById: {}", exception.getMessage());
            return errorApi(exception.getMessage());
        }
    }
}
