package com.jumkid.oauthcentral.controller;

import com.jumkid.oauthcentral.controller.response.CommonResponse;
import com.jumkid.oauthcentral.controller.dto.Authority;
import com.jumkid.oauthcentral.controller.dto.User;
import com.jumkid.oauthcentral.model.UserEntity;
import com.jumkid.oauthcentral.service.AuthorityService;
import com.jumkid.oauthcentral.service.UserService;
import com.jumkid.oauthcentral.utils.UserField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthorityService authorityService;

    @Autowired
    public UserController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/field/{fieldName}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSingleFieldOfAll(@PathVariable String fieldName) {
        return userService.getSingleFieldOfAll(UserField.of(fieldName));
    }

    @GetMapping(path = "{username}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@NotBlank @PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping(value = "/authorities/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<Authority> getUserAuthorities(@PathVariable String username) {
        return authorityService.getAuthorityByUser(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public User add(@NotNull @Valid @RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping(value = "{username}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@NotBlank @PathVariable String username, @NotNull @RequestBody User user) {
        return userService.updateUser(username, user);
    }

    @PatchMapping(value = "{username}")
    @ResponseStatus(HttpStatus.OK)
    public User patch(@PathVariable String username,
                               @NotNull @RequestBody Map<String, Object> updatesMap) {
        return userService.patchUser(username, updatesMap);
    }

    @DeleteMapping(value = "/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public CommonResponse delete(@NotBlank @PathVariable String username) {
        int rows = userService.deleteUser(username);
        return new CommonResponse(String.format("user %s is deleted successfully and remove %d roles", username, rows));
    }

}
