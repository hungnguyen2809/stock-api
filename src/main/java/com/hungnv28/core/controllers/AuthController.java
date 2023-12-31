package com.hungnv28.core.controllers;

import com.hungnv28.core.base.BaseController;
import com.hungnv28.core.base.BaseResponse;
import com.hungnv28.core.controllers.request.SignInRequest;
import com.hungnv28.core.controllers.request.SignUpRequest;
import com.hungnv28.core.controllers.response.SignInResponse;
import com.hungnv28.core.dtos.UserInfoDTO;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.exception.ErrorResponse;
import com.hungnv28.core.models.UserToken;
import com.hungnv28.core.services.UserService;
import com.hungnv28.core.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${sys.api.v1}/auth")
public class AuthController extends BaseController {

    private final UserService userService;

    @PostMapping(value = "/sign-in")
    @Operation(summary = "Đăng nhập")
    public ResponseEntity<BaseResponse> signIn(@RequestBody SignInRequest body) {
        try {
            UserInfoDTO user = userService.loginUser(body.getUsername(), body.getPassword());
            UserToken userToken = UserToken.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .fullName(user.getFullName())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();

            String token = JwtTokenUtil.generateToken(userToken, false);
            String refreshToken = JwtTokenUtil.generateToken(userToken, true);

            return successApi(SignInResponse.builder()
                    .userInfo(user)
                    .token(token)
                    .refreshToken(refreshToken)
                    .build());
        } catch (ApiException exception) {
            log.error("AuthController_signIn: {}", exception.getMessage());
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("AuthController_signIn: {}", exception.getMessage());
            return errorApi(exception.getMessage());
        }
    }

    @PostMapping(value = "/sign-up")
    @Operation(summary = "Đăng ký")
    public ResponseEntity<BaseResponse> signUp(@RequestBody SignUpRequest body) {
        try {
            boolean result = userService.registerUser(body);
            if (!result) {
                return errorApi("Đăng ký tài khoản thất bại");
            }

            return successApi(null, "Đăng ký tài khoản thành công");
        } catch (ApiException exception) {
            log.error("AuthController_signUp: {}", exception.getMessage());
            return errorApi(new ErrorResponse(exception));
        } catch (Exception exception) {
            log.error("AuthController_signUp: {}", exception.getMessage());
            return errorApi(exception.getMessage());
        }
    }
}
