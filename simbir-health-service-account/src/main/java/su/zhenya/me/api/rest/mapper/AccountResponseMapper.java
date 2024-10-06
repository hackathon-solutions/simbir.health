package su.zhenya.me.api.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.api.rest.response.AccountResponse;

@Mapper
public interface AccountResponseMapper {

    @Mapping(target = "accountRoles", source = "roles")
    AccountResponse domainToResponse(Account entity);
}
