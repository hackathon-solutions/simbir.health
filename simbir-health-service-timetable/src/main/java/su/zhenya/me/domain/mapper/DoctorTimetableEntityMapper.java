package su.zhenya.me.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.domain.entity.DoctorTimetableEntity;
import su.zhenya.me.hospital.model.Hospital;
import su.zhenya.me.timetable.model.DoctorTimetable;

@Mapper(imports = {Hospital.class, Account.class})
public interface DoctorTimetableEntityMapper {

    @Mapping(target = "hospitalId", expression = "java(doctorTimetable.getHospital().getHospitalId())")
    @Mapping(target = "doctorId", expression = "java(doctorTimetable.getDoctor().getAccountId())")
    DoctorTimetableEntity domainToEntity(DoctorTimetable doctorTimetable);

    @Mapping(target = "hospital", expression = "java(new Hospital(doctorTimetable.getHospitalId()))")
    @Mapping(target = "doctor", expression = "java(new Account(doctorTimetable.getDoctorId()))")
    DoctorTimetable entityToDomain(DoctorTimetableEntity doctorTimetable);
}
