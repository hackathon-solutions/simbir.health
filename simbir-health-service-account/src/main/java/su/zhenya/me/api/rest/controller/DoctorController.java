package su.zhenya.me.api.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.api.rest.mapper.AccountResponseMapper;
import su.zhenya.me.api.rest.response.AccountResponse;
import su.zhenya.me.common.security.bean.authority.annotation.OnlyAuthorized;
import su.zhenya.me.domain.service.doctor.DoctorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.rest-api.controllers.doctor.path}")
public class DoctorController {

    private final DoctorService doctorService;
    private final AccountResponseMapper accountResponseMapper;

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.doctor.endpoints.doctors-get-all}")
    public Page<AccountResponse> accountDoctorsGetAll(
            @RequestParam(required = false, defaultValue = "") String filterName,
            @ParameterObject Pageable pageable
    ) {
        return doctorService.getAllAccountsFilteredBy(filterName, pageable)
                            .map(accountResponseMapper::domainToResponse);
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.doctor.endpoints.doctors-get-by-id}")
    public AccountResponse accountDoctorGetById(@PathVariable long accountId) {
        return accountResponseMapper.domainToResponse(doctorService.getDoctorBy(new AccountId(accountId)));
    }
}
