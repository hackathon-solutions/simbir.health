package su.zhenya.me.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service.account.api.controllers[0].path}")
public class AuthenticationController {

    @PostMapping("${service.account.api.controllers[0].endpoints.account-sign-up}")
    public void signUp() {
    }

    @PostMapping("${service.account.api.controllers[0].endpoints.account-sign-in}")
    public void signIn() {
    }

    @PutMapping("${service.account.api.controllers[0].endpoints.token-ban}")
    public void tokenBan() {
    }

    @GetMapping("${service.account.api.controllers[0].endpoints.token-validate}")
    public void tokenValidate() {
    }

    @PostMapping("${service.account.api.controllers[0].endpoints.token-refresh}")
    public void tokenRefresh() {
    }
}
