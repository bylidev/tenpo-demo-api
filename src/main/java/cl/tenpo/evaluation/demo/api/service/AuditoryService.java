package cl.tenpo.evaluation.demo.api.service;

import cl.tenpo.evaluation.demo.api.model.Auditory;
import cl.tenpo.evaluation.demo.api.model.HttpAuditory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditoryService {
    void save(Auditory auditory);
    Page<HttpAuditory> findHttpAuditory(Pageable pageable);
}
