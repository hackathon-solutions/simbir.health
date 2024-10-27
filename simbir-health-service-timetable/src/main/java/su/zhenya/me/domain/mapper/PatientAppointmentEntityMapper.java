package su.zhenya.me.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.domain.entity.PatientAppointmentEntity;
import su.zhenya.me.timetable.model.PatientAppointment;

@Mapper(imports = {Account.class})
public interface PatientAppointmentEntityMapper {

    @Mapping(target = "doctor", expression = "java(new Account(entity.getDoctorTimetable().getDoctorId()))")
    @Mapping(target = "patient", expression = "java(new Account(entity.getPatientId()))")
    @Mapping(target = "room", expression = "java(entity.getDoctorTimetable().getRoom())")
    PatientAppointment entityToDomain(PatientAppointmentEntity entity);

    PatientAppointmentEntity domainToEntity(PatientAppointment domain);
}
