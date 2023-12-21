package com.hungnv28.core.controllers;

import com.hungnv28.core.base.BaseController;
import com.hungnv28.core.base.BaseResponse;
import com.hungnv28.core.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController extends BaseController {

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/")
    public ResponseEntity<BaseResponse> hello() {
        return successApi("Hello, My name is nvh28");
    }
}
