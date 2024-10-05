package su.zhenya.me.api.rest.controller.mapper;

import org.mapstruct.Mapper;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.api.rest.controller.dto.AccountCredentialsDto;

@Mapper
public interface AccountDtoMapper {

    AccountCredentials domainToDto(AccountCredentialsDto accountCredentials);
}
