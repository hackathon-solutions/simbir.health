package su.zhenya.me.api.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.api.rest.request.CreateDoctorTimetableRequest;
import su.zhenya.me.api.rest.request.UpdateDoctorTimetableRequest;
import su.zhenya.me.hospital.model.Hospital;
import su.zhenya.me.timetable.model.DoctorTimetable;

@Mapper(imports = {Account.class, Hospital.class})
public interface TimetableRequestMapper {

    @Mapping(target = "hospital", expression = "java(new Hospital(request.getHospitalId()))")
    @Mapping(target = "doctor", expression = "java(new Account(request.getDoctorId()))")
    DoctorTimetable requestToDomain(CreateDoctorTimetableRequest request);

    @Mapping(target = "hospital", expression = "java(new Hospital(request.getHospitalId()))")
    @Mapping(target = "doctor", expression = "java(new Account(request.getDoctorId()))")
    DoctorTimetable requestToDomain(UpdateDoctorTimetableRequest request);
}
