package su.zhenya.me.api.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service.account.api.controllers.authentication.path}")
public class AuthenticationController {

    @PostMapping("${service.account.api.controllers.authentication.endpoints.account-sign-up}")
    public void signUp() {
    }

    @PostMapping("${service.account.api.controllers.authentication.endpoints.account-sign-in}")
    public void signIn() {
    }

    @PutMapping("${service.account.api.controllers.authentication.endpoints.token-ban}")
    public void tokenBan() {
    }

    @GetMapping("${service.account.api.controllers.authentication.endpoints.token-validate}")
    public void tokenValidate() {
    }

    @PostMapping("${service.account.api.controllers.authentication.endpoints.token-refresh}")
    public void tokenRefresh() {
    }
}
