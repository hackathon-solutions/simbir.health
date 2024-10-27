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
import su.zhenya.me.hospital.model.HospitalId;

// TODO: это уже явное дублирование (из сервиса больниц), вынести в общий модуль
public class HospitalIdType implements UserType<HospitalId>, EnhancedUserType<HospitalId> {

    @Override
    public int getSqlType() {
        return Types.BIGINT;
    }

    @Override
    public Class<HospitalId> returnedClass() {
        return HospitalId.class;
    }

    @Override
    public boolean equals(HospitalId hospitalId, HospitalId j1) {
        return hospitalId.equals(j1);
    }

    @Override
    public int hashCode(HospitalId hospitalId) {
        return hospitalId.hashCode();
    }

    @Override
    public HospitalId nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws SQLException {
        return new HospitalId(resultSet.getLong(i));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, HospitalId hospitalId, int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        preparedStatement.setLong(i, hospitalId.getId());
    }

    @Override
    public HospitalId deepCopy(HospitalId hospitalId) {
        return new HospitalId(hospitalId.getId());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(HospitalId hospitalId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HospitalId assemble(Serializable serializable, Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toSqlLiteral(HospitalId hospitalId) {
        return hospitalId.getId() + "";
    }

    @Override
    public String toString(HospitalId hospitalId) throws HibernateException {
        return toSqlLiteral(hospitalId);
    }

    @Override
    public HospitalId fromStringValue(CharSequence charSequence) throws HibernateException {
        return new HospitalId(Long.parseLong(charSequence.toString()));
    }
}
