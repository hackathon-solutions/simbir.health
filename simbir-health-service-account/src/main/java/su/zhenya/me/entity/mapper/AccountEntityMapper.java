package su.zhenya.me.entity.mapper;

import org.mapstruct.Mapper;
import su.zhenya.me.account.model.Account;

@Mapper
public interface AccountEntityMapper {

    Account entityToModel(su.zhenya.me.entity.Account account);
}
