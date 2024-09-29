package su.zhenya.me.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service.account.api.controllers[2].path}")
public class DoctorController {

    @GetMapping("${service.account.api.controllers[2].endpoints.doctors-get-all}")
    public void accountDoctorsGetAll() {
    }

    @GetMapping("${service.account.api.controllers[2].endpoints.doctors-get-by-id}")
    public void accountDoctorGetById(@PathVariable long accountId) {
    }
}
