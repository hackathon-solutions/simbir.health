package su.zhenya.me.domain.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.Role;

@Converter(autoApply = true)
public class RoleListToStringConverter implements AttributeConverter<List<Role>, String> {

    @Override
    public String convertToDatabaseColumn(List<Role> roles) {
        return Account.listRolesToStringRoles(roles);
    }

    @Override
    public List<Role> convertToEntityAttribute(String s) {
        return Account.stringRolesToListRoles(s);
    }
}
