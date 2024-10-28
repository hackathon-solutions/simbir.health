package su.zhenya.me.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.zhenya.me.common.exception.ReplaceException;
import su.zhenya.me.document.model.HistoryId;
import su.zhenya.me.document.model.PatientAppointmentHistory;
import su.zhenya.me.entity.HistoryEntity;
import su.zhenya.me.entity.mapper.HistoryEntityMapper;
import su.zhenya.me.repository.HistoryRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryEntityMapper historyEntityMapper;

    public HistoryId saveHistory(PatientAppointmentHistory history) {
        return historyRepository.save(historyEntityMapper.domainToEntity(history)).getHistoryId();
    }

    public PatientAppointmentHistory getHistory(HistoryId historyId) {
        return historyEntityMapper.entityToDomain(historyRepository.findById(historyId).orElseThrow(ReplaceException::new));
    }

    public List<PatientAppointmentHistory> getAll() {
        Iterator<HistoryEntity> source = historyRepository.findAll().iterator();
        List<PatientAppointmentHistory> target = new ArrayList<>();
        source.forEachRemaining(history -> target.add(historyEntityMapper.entityToDomain(history)));
        return target;
    }
}
