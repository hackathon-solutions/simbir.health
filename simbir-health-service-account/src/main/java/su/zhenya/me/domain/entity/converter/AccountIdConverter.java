package su.zhenya.me.domain.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import su.zhenya.me.account.model.AccountId;

@Converter(autoApply = true)
public class AccountIdConverter implements AttributeConverter<AccountId, Long> {

    @Override
    public Long convertToDatabaseColumn(AccountId accountId) {
        return accountId.getId();
    }

    @Override
    public AccountId convertToEntityAttribute(Long aLong) {
        return new AccountId(aLong);
    }
}
