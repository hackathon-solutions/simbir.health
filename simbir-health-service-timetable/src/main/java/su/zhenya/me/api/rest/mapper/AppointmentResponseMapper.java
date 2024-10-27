package su.zhenya.me.api.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.zhenya.me.api.rest.response.AvailablePatientAppointmentResponse;
import su.zhenya.me.timetable.model.PatientAppointment;

@Mapper
public interface AppointmentResponseMapper {

    @Mapping(target = "doctorId", expression = "java(patientAppointment.getDoctor().getAccountId())")
    AvailablePatientAppointmentResponse domainToResponse(PatientAppointment patientAppointment);
}
