package su.zhenya.me.api.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountToken;
import su.zhenya.me.api.rest.request.AccountCreateRequest;
import su.zhenya.me.api.rest.request.AccountSelfUpdateRequest;
import su.zhenya.me.api.rest.request.AccountSignUpRequest;
import su.zhenya.me.api.rest.request.AccountTokenRequest;
import su.zhenya.me.api.rest.request.AccountUpdateRequest;

@Mapper
public interface AccountRequestMapper {

    Account requestToDomain(AccountCreateRequest request);
    Account requestToDomain(AccountUpdateRequest request);
    @Mapping(target = "roles", ignore = true)
    Account requestToDomain(AccountSelfUpdateRequest request);
    Account requestToDomain(AccountSignUpRequest request);
    AccountToken requestToDomain(AccountTokenRequest request);
}
