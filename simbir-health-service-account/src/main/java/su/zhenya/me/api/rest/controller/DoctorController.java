package su.zhenya.me.api.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service.account.api.controllers.doctor.path}")
public class DoctorController {

    @GetMapping("${service.account.api.controllers.doctor.endpoints.doctors-get-all}")
    public void accountDoctorsGetAll() {
    }

    @GetMapping("${service.account.api.controllers.doctor.endpoints.doctors-get-by-id}")
    public void accountDoctorGetById(@PathVariable long accountId) {
    }
}
