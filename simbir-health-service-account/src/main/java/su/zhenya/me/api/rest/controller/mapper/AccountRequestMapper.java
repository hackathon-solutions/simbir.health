package su.zhenya.me.api.rest.controller.mapper;

import org.mapstruct.Mapper;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.api.rest.controller.request.AccountCreateRequest;
import su.zhenya.me.api.rest.controller.request.AccountUpdateRequest;

@Mapper
public interface AccountRequestMapper {

    Account requestToDomain(AccountCreateRequest request);
    Account requestToDomain(AccountUpdateRequest request);
}
