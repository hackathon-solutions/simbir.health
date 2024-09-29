package su.zhenya.me.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service.account.api.controllers[1].path}")
public class AccountController {

    @GetMapping("${service.account.api.controllers[1].endpoints.account-get-current}")
    public void accountGetCurrent() {
    }

    @PutMapping("${service.account.api.controllers[1].endpoints.account-patch-current}")
    public void accountPatchCurrent() {
    }

    @GetMapping("${service.account.api.controllers[1].endpoints.account-get-all}")
    public void accountGetAll() {
    }

    @PostMapping("${service.account.api.controllers[1].endpoints.account-create}")
    public void accountCreate() {
    }

    @PutMapping("${service.account.api.controllers[1].endpoints.account-patch-by-id}")
    public void accountPathById(@PathVariable long accountId) {
    }

    @DeleteMapping("${service.account.api.controllers[1].endpoints.account-delete-by-id}")
    public void accountDeleteById(@PathVariable long accountId) {
    }
}
