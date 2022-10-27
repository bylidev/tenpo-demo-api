package cl.tenpo.evaluation.demo.api.repository;

import cl.tenpo.evaluation.demo.api.model.Auditory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoryRepository<T extends Auditory> extends JpaRepository<T,Long> {
    Page<T> findAll(Pageable pageable);
}
