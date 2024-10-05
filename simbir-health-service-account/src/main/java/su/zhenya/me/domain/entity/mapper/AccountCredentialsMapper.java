package su.zhenya.me.domain.entity.mapper;

import org.mapstruct.Mapper;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.domain.entity.AccountCredentialsEntity;

@Mapper
public interface AccountCredentialsMapper {

    AccountCredentials entityToDomain(AccountCredentialsEntity credentials);
    AccountCredentialsEntity domainToEntity(AccountCredentials credentials);
}
