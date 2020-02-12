package com.jumkid.oauthcentral.controller;

import com.jumkid.oauthcentral.controller.response.CommonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/status")
public class StatusCheckController {

    public enum ServiceStatus {
        OK("OK"), ERROR("ERROR"), WARNING("WARNING");

        private String value;

        ServiceStatus(String value) { this.value = value; }

        public String value() { return value; }
    }

    @GetMapping(produces = "application/json")
    @ResponseBody
    public CommonResponse<String> status() {
        return new CommonResponse<>(ServiceStatus.OK.value());
    }

}
