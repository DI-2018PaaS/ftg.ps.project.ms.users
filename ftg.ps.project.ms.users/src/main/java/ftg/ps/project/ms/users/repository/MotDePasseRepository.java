package ftg.ps.project.ms.users.repository;

import ftg.ps.project.ms.users.domain.MotDePasse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MotDePasse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotDePasseRepository extends JpaRepository<MotDePasse, Long> {

}
