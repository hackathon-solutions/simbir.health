package su.zhenya.me.api.rest.mapper;

import org.mapstruct.Mapper;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.api.rest.dto.AccountCredentialsDto;

@Mapper
public interface AccountDtoMapper {

    AccountCredentials domainToDto(AccountCredentialsDto accountCredentials);
}
