package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRespository  extends JpaRepository<  Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            SELECT m FROM Medico m WHERE m.activo = 1 AND
            m.id NOT IN(SELECT c.medico.id FROM Consulta c c.data=:fecha) AND
            m.especialidad=:especialidad ORDER BY RAND() LIMIT 1
            """)
    Medico obtenerMedicoPorEspecialidadYFecha(Especialidad especialidad, LocalDateTime fecha);
}
