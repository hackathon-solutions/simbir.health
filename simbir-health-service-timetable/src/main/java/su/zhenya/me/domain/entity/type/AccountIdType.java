package su.zhenya.me.domain.entity.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.UserType;
import su.zhenya.me.account.model.AccountId;

// TODO: это уже явное дублирование (из сервиса аккаунтов), вынести в общий модуль
// TODO: очень похожие концепции (является айдишником с одним полем), рассмотреть возможность общей абстракции
public class AccountIdType implements UserType<AccountId>, EnhancedUserType<AccountId> {

    @Override
    public int getSqlType() {
        return Types.BIGINT;
    }

    @Override
    public Class<AccountId> returnedClass() {
        return AccountId.class;
    }

    @Override
    public boolean equals(AccountId accountId, AccountId j1) {
        return accountId.equals(j1);
    }

    @Override
    public int hashCode(AccountId accountId) {
        return accountId.hashCode();
    }

    @Override
    public AccountId nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws SQLException {
        return new AccountId(resultSet.getLong(i));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, AccountId accountId, int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        preparedStatement.setLong(i, accountId.getId());
    }

    @Override
    public AccountId deepCopy(AccountId accountId) {
        return new AccountId(accountId.getId());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(AccountId accountId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AccountId assemble(Serializable serializable, Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toSqlLiteral(AccountId accountId) {
        return accountId.getId() + "";
    }

    @Override
    public String toString(AccountId accountId) throws HibernateException {
        return toSqlLiteral(accountId);
    }

    @Override
    public AccountId fromStringValue(CharSequence charSequence) throws HibernateException {
        return new AccountId(Long.parseLong(charSequence.toString()));
    }
}
