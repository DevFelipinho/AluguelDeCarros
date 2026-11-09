package web.trabalhodaw1.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.trabalhodaw1.common.enums.Status;
import web.trabalhodaw1.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    Page<Carro> findAllByStatus(Status status, Pageable pageable);

    Optional<Carro> findByIdAndStatus(Long id, Status status);

    boolean existsByPlacaAndStatus(String placa, Status status);

    boolean existsByPlacaAndIdNotAndStatus(String placa, Long id, Status status);

}
