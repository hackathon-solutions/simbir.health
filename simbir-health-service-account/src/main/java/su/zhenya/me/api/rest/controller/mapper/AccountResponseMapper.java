package su.zhenya.me.api.rest.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.api.rest.controller.response.AccountResponse;

@Mapper
public interface AccountResponseMapper {

    @Mapping(target = "accountRole", source = "role")
    AccountResponse domainToResponse(Account entity);
}
