package su.zhenya.me.domain.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.domain.entity.AccountEntity;

@Mapper
public interface AccountEntityMapper {

    @Mapping(target = "role", source = "accountRole")
    Account entityToDomain(AccountEntity account);
    @Mapping(target = "accountRole", source = "role", conditionExpression = "java(account.getRole() != null)")
    AccountEntity domainToEntity(Account account);
}
