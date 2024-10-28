package su.zhenya.me.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import su.zhenya.me.document.model.HistoryId;
import su.zhenya.me.entity.HistoryEntity;

@Repository
public interface HistoryRepository extends ElasticsearchRepository<HistoryEntity, HistoryId> {
}
