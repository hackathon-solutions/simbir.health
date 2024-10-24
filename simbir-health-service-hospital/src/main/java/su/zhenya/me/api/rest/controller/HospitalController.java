package su.zhenya.me.api.rest.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
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
import su.zhenya.me.api.rest.mapper.HospitalRequestMapper;
import su.zhenya.me.api.rest.request.CreateHospitalRequest;
import su.zhenya.me.api.rest.request.UpdateHospitalRequest;
import su.zhenya.me.common.security.bean.authority.annotation.HasRole;
import su.zhenya.me.common.security.bean.authority.annotation.OnlyAuthorized;
import su.zhenya.me.domain.query.HospitalQueryService;
import su.zhenya.me.domain.service.HospitalService;
import su.zhenya.me.hospital.model.Hospital;
import su.zhenya.me.hospital.model.HospitalId;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.rest-api.controllers.hospital.path}")
public class HospitalController {

    private final HospitalRequestMapper hospitalRequestMapper;
    private final HospitalService hospitalService;
    private final HospitalQueryService hospitalQueryService;

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.hospital.endpoints.hospital-get-all}")
    public Page<Hospital> getAllHospitals(@ParameterObject Pageable pageable) {
        return hospitalQueryService.getAllHospitals(pageable);
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.hospital.endpoints.get-hospital-by-id}")
    public Hospital getHospitalById(@PathVariable long hospitalId) {
        return hospitalQueryService.getHospitalById(new HospitalId(hospitalId));
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.hospital.endpoints.get-hospital-rooms-by-hospital-id}")
    public List<String> getHospitalRoomsByHospitalId(@PathVariable long hospitalId) {
        return hospitalQueryService.getHospitalRoomsByHospitalId(new HospitalId(hospitalId));
    }

    @HasRole(Role.ADMIN)
    @PostMapping("${service.rest-api.controllers.hospital.endpoints.create-hospital-post}")
    public HospitalId createHospital(@RequestBody CreateHospitalRequest request) {
        return hospitalService.createHospital(hospitalRequestMapper.requestToDomain(request));
    }

    @HasRole(Role.ADMIN)
    @PutMapping("${service.rest-api.controllers.hospital.endpoints.hospital-patch-by-id}")
    public Hospital hospitalPatchById(@PathVariable long hospitalId, @RequestBody UpdateHospitalRequest request) {
        return hospitalService.updateHospitalById(new HospitalId(hospitalId), hospitalRequestMapper.requestToDomain(request));
    }

    // TODO: реализовать удаление
    @HasRole(Role.ADMIN)
    @DeleteMapping("${service.rest-api.controllers.hospital.endpoints.hospital-delete-by-id}")
    public void hospitalDeleteById(@PathVariable HospitalId hospitalId) {
    }
}
