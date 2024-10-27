package su.zhenya.me.api.rest.request.validation;

import su.zhenya.me.api.rest.request.UpdateDoctorTimetableRequest;
import su.zhenya.me.common.validation.ValidationUtils;
import su.zhenya.me.common.validation.core.Validation;
import su.zhenya.me.common.validation.core.ValidationException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UpdateDoctorTimetableRequestValidation implements Validation {
    @Override
    public void validate(Object o) throws ValidationException {
        if (o instanceof UpdateDoctorTimetableRequest request) {
            validate(request);
        }
    }

    // TODO: мб, в утилсы вынести этот момент
    private void validate(UpdateDoctorTimetableRequest request) throws ValidationException {
        ValidationUtils.validateThrowIf(() -> isNotTimeMultiple30Minutes(request.getFrom()));
        ValidationUtils.validateThrowIf(() -> isNotTimeMultiple30Minutes(request.getTo()));
        ValidationUtils.validateThrowIf(() -> isTimesBetweenTooLarge(request.getFrom(), request.getTo()));
    }

    private static boolean isNotTimeMultiple30Minutes(LocalDateTime time) {
        return time.getMinute() % 30 != 0 && time.getSecond() != 0 && time.getNano() != 0;
    }

    private static boolean isTimesBetweenTooLarge(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).get(ChronoUnit.SECONDS) > Duration.ofHours(12).get(ChronoUnit.SECONDS);
    }
}
