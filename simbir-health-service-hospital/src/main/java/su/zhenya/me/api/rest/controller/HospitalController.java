package su.zhenya.me.api.rest.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.common.security.bean.authority.annotation.HasRole;
import su.zhenya.me.common.security.bean.authority.annotation.OnlyAuthorized;
import su.zhenya.me.hospital.model.Hospital;
import su.zhenya.me.hospital.model.HospitalId;
import su.zhenya.me.hospital.model.Room;

@RestController
@RequestMapping("${service.rest-api.controllers.hospital.path}")
public class HospitalController {

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.hospital.endpoints.hospital-get-all}")
    public Page<Hospital> getAllHospitals(@ParameterObject Pageable pageable) {
        return null;
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.hospital.endpoints.get-hospital-by-id}")
    public Hospital getHospitalById(@PathVariable HospitalId hospitalId) {
        return null;
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.hospital.endpoints.get-hospital-rooms-by-hospital-id}")
    public Page<Room> getHospitalRoomsByHospitalId(@PathVariable HospitalId hospitalId) {
        return null;
    }

    @HasRole(Role.ADMIN)
    @PostMapping("${service.rest-api.controllers.hospital.endpoints.create-hospital-post}")
    public HospitalId createHospital(@RequestBody Hospital hospital) {
        return null;
    }

    @HasRole(Role.ADMIN)
    @PutMapping("${service.rest-api.controllers.hospital.endpoints.hospital-patch-by-id}")
    public Hospital hospitalPatchById(@PathVariable HospitalId hospitalId, @RequestBody Hospital hospital) {
        return null;
    }

    @HasRole(Role.ADMIN)
    @DeleteMapping("${service.rest-api.controllers.hospital.endpoints.hospital-delete-by-id}")
    public void hospitalDeleteById(@PathVariable HospitalId hospitalId) {
    }
}
