package su.zhenya.me.domain.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.domain.entity.AccountEntity;

@Mapper
public interface AccountEntityMapper {

    @Mapping(target = "roles", source = "accountRoles")
    Account entityToDomain(AccountEntity account);
    @Mapping(target = "accountRoles", source = "roles", conditionExpression = "java(account.getRoles() != null && !account.getRoles().isEmpty())")
    AccountEntity domainToEntity(Account account);
}
