package su.zhenya.me.entity.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.UserType;
import su.zhenya.me.document.model.HistoryId;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class HistoryIdType implements UserType<HistoryId>, EnhancedUserType<HistoryId> {

    @Override
    public int getSqlType() {
        return SqlTypes.VARCHAR;
    }

    @Override
    public Class<HistoryId> returnedClass() {
        return HistoryId.class;
    }

    @Override
    public boolean equals(HistoryId historyId, HistoryId j1) {
        return historyId.equals(j1);
    }

    @Override
    public int hashCode(HistoryId historyId) {
        return historyId.hashCode();
    }

    @Override
    public HistoryId nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws SQLException {
        return new HistoryId(UUID.fromString(resultSet.getString(i)));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, HistoryId historyId, int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        preparedStatement.setString(i, historyId.getId().toString());
    }

    @Override
    public HistoryId deepCopy(HistoryId historyId) {
        return new HistoryId(historyId.getId());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(HistoryId historyId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HistoryId assemble(Serializable serializable, Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toSqlLiteral(HistoryId historyId) {
        return historyId.getId().toString();
    }

    @Override
    public String toString(HistoryId historyId) throws HibernateException {
        return toSqlLiteral(historyId);
    }

    @Override
    public HistoryId fromStringValue(CharSequence charSequence) throws HibernateException {
        return new HistoryId(UUID.fromString(charSequence.toString()));
    }
}
