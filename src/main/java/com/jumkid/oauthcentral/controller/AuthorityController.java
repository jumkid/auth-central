package com.jumkid.oauthcentral.controller;

import com.jumkid.oauthcentral.controller.response.CommonResponse;
import com.jumkid.oauthcentral.controller.dto.Authority;
import com.jumkid.oauthcentral.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping
    @ResponseBody
    public Authority add(@NotNull @Valid @RequestBody Authority authority){
        return authorityService.saveAuthority(authority);
    }

    @PutMapping(value = "{authorityId}")
    @ResponseBody
    public Authority add(@NotNull @PathVariable Integer authorityId, @NotNull @Valid @RequestBody Authority authority){
        return authorityService.updateAuthority(authorityId, authority);
    }

    @DeleteMapping(value = "{authorityId}")
    @ResponseBody
    public CommonResponse delete(@NotNull @PathVariable Integer authorityId) {
        authorityService.deleteAuthority(authorityId);
        return new CommonResponse("User authority is deleted");
    }

}
