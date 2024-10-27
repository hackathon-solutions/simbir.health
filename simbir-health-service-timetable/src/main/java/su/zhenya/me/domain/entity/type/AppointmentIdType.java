package su.zhenya.me.domain.entity.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.UserType;
import su.zhenya.me.timetable.model.AppointmentId;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AppointmentIdType implements UserType<AppointmentId>, EnhancedUserType<AppointmentId> {

    @Override
    public int getSqlType() {
        return SqlTypes.VARCHAR;
    }

    @Override
    public Class<AppointmentId> returnedClass() {
        return AppointmentId.class;
    }

    @Override
    public boolean equals(AppointmentId appointmentId, AppointmentId j1) {
        return appointmentId.equals(j1);
    }

    @Override
    public int hashCode(AppointmentId appointmentId) {
        return appointmentId.hashCode();
    }

    @Override
    public AppointmentId nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws SQLException {
        return new AppointmentId(UUID.fromString(resultSet.getString(i)));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, AppointmentId appointmentId, int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        preparedStatement.setString(i, appointmentId.getId().toString());
    }

    @Override
    public AppointmentId deepCopy(AppointmentId appointmentId) {
        return new AppointmentId(appointmentId.getId());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(AppointmentId appointmentId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AppointmentId assemble(Serializable serializable, Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toSqlLiteral(AppointmentId appointmentId) {
        return appointmentId.getId().toString();
    }

    @Override
    public String toString(AppointmentId appointmentId) throws HibernateException {
        return toSqlLiteral(appointmentId);
    }

    @Override
    public AppointmentId fromStringValue(CharSequence charSequence) throws HibernateException {
        return new AppointmentId(UUID.fromString(charSequence.toString()));
    }
}
