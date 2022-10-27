package cl.tenpo.evaluation.demo.api.service;

import cl.tenpo.evaluation.demo.api.model.Auditory;
import cl.tenpo.evaluation.demo.api.model.HttpAuditory;
import cl.tenpo.evaluation.demo.api.repository.AuditoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuditoryServiceImpl implements AuditoryService {

    @Autowired
    private AuditoryRepository auditoryRepository;

    @Override
    public void save(Auditory auditory) {
        this.auditoryRepository.save(auditory);
    }

    @Override
    public Page<HttpAuditory> findHttpAuditory(Pageable pageable) {
        return this.auditoryRepository.findAll(pageable);
    }
}
