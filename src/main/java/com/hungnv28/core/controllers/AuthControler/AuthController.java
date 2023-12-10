package com.hungnv28.core.controllers.AuthControler;

import com.hungnv28.core.base.BaseController;
import com.hungnv28.core.base.BaseResponse;
import com.hungnv28.core.controllers.AuthControler.request.AuthSignInRequest;
import com.hungnv28.core.controllers.AuthControler.response.SignInResponse;
import com.hungnv28.core.entities.Users;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.exception.ErrorResponse;
import com.hungnv28.core.services.UserService;
import com.hungnv28.core.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final UserService userService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<BaseResponse> signIn(@RequestBody AuthSignInRequest body) {
        try {
            Users users = userService.checkUser(body.getUsername(), body.getPassword());
            String token = JwtTokenUtil.generateToken(users, false);
            String refresh_token = JwtTokenUtil.generateToken(users, true);

            return successApi(new SignInResponse(users, token, refresh_token));
        } catch (ApiException exception) {
            log.error("AuthController_signIn: {}", exception.getMessage(), exception);
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("AuthController_signIn: {}", exception.getMessage(), exception);
            return errorApi(exception.getMessage());
        }
    }
}
