package su.zhenya.me.api.rest.mapper;

import org.mapstruct.Mapper;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.api.rest.request.AccountCreateRequest;
import su.zhenya.me.api.rest.request.AccountUpdateRequest;

@Mapper
public interface AccountRequestMapper {

    Account requestToDomain(AccountCreateRequest request);
    Account requestToDomain(AccountUpdateRequest request);
}
