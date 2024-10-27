package su.zhenya.me.api.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.api.rest.response.DoctorTimetableResponse;
import su.zhenya.me.hospital.model.Hospital;
import su.zhenya.me.timetable.model.DoctorTimetable;

@Mapper(imports = {Account.class, Hospital.class})
public interface TimetableResponseMapper {

    @Mapping(target = "hospitalId", expression = "java(doctorTimetable.getHospital().getHospitalId())")
    @Mapping(target = "doctorId", expression = "java(doctorTimetable.getDoctor().getAccountId())")
    DoctorTimetableResponse domainToResponse(DoctorTimetable doctorTimetable);
}
