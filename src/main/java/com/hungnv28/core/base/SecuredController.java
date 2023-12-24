package com.hungnv28.core.base;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "Authorization")
public class SecuredController extends BaseController {
}
