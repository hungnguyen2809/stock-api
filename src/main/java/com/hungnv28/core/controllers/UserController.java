package com.hungnv28.core.controllers;

import com.hungnv28.core.base.BaseController;
import com.hungnv28.core.base.BaseResponse;
import com.hungnv28.core.entities.UsersEntity;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.exception.ErrorResponse;
import com.hungnv28.core.models.UserInfo;
import com.hungnv28.core.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${sys.api.v1}/user")
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> getUserById(@PathVariable String id) {
        try {
            UserInfo userInfo = getCurrentUser();
            log.info(userInfo.getFullName());
            UsersEntity users = userService.findUserById(id);
            return successApi(users);
        } catch (ApiException exception) {
            log.error("UserController_getUserById: {}", exception.getMessage(), exception);
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("UserController_getUserById: {}", exception.getMessage(), exception);
            return errorApi(exception.getMessage());
        }
    }
}
