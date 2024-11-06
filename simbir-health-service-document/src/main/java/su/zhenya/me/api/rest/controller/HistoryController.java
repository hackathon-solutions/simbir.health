package su.zhenya.me.api.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import su.zhenya.me.common.security.bean.authority.annotation.OnlyAuthorized;
import su.zhenya.me.document.model.HistoryId;
import su.zhenya.me.document.model.PatientAppointmentHistory;
import su.zhenya.me.service.HistoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.rest-api.controllers.history.path}")
public class HistoryController {

    private final HistoryService historyService;

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.history.endpoints.get-history-by-account-id}")
    public List<PatientAppointmentHistory> getAccountHistory(@PathVariable long accountId) {
        return historyService.getAll().stream()
                .filter(history -> history.getPatientAppointment().getPatient().getAccountId().getId() == accountId)
                .toList();
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.history.endpoints.get-history-by-id}")
    public PatientAppointmentHistory getAccountHistoryRecord(@RequestParam UUID historyId) {
        return historyService.getHistory(new HistoryId(historyId));
    }

    @OnlyAuthorized
    @PostMapping("${service.rest-api.controllers.history.endpoints.create-patient-appointment-history}")
    public HistoryId createPatientAppointmentHistory(@RequestBody PatientAppointmentHistory patientAppointmentHistory) {
        return historyService.saveHistory(patientAppointmentHistory);
    }

    @PutMapping("${service.rest-api.controllers.history.endpoints.update-history-by-id}")
    public PatientAppointmentHistory updatePatientAppointmentHistory(HistoryId historyId) {
        return null;
    }
}
