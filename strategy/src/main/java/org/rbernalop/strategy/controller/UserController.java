package org.rbernalop.strategy.controller;

import lombok.RequiredArgsConstructor;
import org.rbernalop.strategy.service.UserCreator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserCreator userCreator;

    @PostMapping("processUsers")
    @ResponseStatus(HttpStatus.CREATED)
    void processUsers(@RequestParam("file") MultipartFile file) {
        userCreator.saveUsersFromFile(file);
    }
}
