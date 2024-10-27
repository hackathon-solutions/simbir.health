package su.zhenya.me.domain.entity.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.UserType;
import su.zhenya.me.timetable.model.TimetableId;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TimetableIdType implements UserType<TimetableId>, EnhancedUserType<TimetableId> {

    @Override
    public int getSqlType() {
        return SqlTypes.VARCHAR;
    }

    @Override
    public Class<TimetableId> returnedClass() {
        return TimetableId.class;
    }

    @Override
    public boolean equals(TimetableId timetableId, TimetableId j1) {
        return timetableId.equals(j1);
    }

    @Override
    public int hashCode(TimetableId timetableId) {
        return timetableId.hashCode();
    }

    @Override
    public TimetableId nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws SQLException {
        return new TimetableId(UUID.fromString(resultSet.getString(i)));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, TimetableId timetableId, int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        preparedStatement.setString(i, timetableId.getId().toString());
    }

    @Override
    public TimetableId deepCopy(TimetableId timetableId) {
        return new TimetableId(timetableId.getId());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(TimetableId timetableId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TimetableId assemble(Serializable serializable, Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toSqlLiteral(TimetableId timetableId) {
        return timetableId.getId().toString();
    }

    @Override
    public String toString(TimetableId timetableId) throws HibernateException {
        return toSqlLiteral(timetableId);
    }

    @Override
    public TimetableId fromStringValue(CharSequence charSequence) throws HibernateException {
        return new TimetableId(UUID.fromString(charSequence.toString()));
    }
}
