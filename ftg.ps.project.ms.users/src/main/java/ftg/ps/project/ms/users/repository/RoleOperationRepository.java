package ftg.ps.project.ms.users.repository;

import ftg.ps.project.ms.users.domain.RoleOperation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RoleOperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleOperationRepository extends JpaRepository<RoleOperation, Long> {

}
