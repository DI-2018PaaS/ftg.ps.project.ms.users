package ftg.ps.project.ms.users.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.users.domain.RoleOperation;
import ftg.ps.project.ms.users.repository.RoleOperationRepository;
import ftg.ps.project.ms.users.web.rest.errors.BadRequestAlertException;
import ftg.ps.project.ms.users.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RoleOperation.
 */
@RestController
@RequestMapping("/api")
public class RoleOperationResource {

    private final Logger log = LoggerFactory.getLogger(RoleOperationResource.class);

    private static final String ENTITY_NAME = "roleOperation";

    private final RoleOperationRepository roleOperationRepository;

    public RoleOperationResource(RoleOperationRepository roleOperationRepository) {
        this.roleOperationRepository = roleOperationRepository;
    }

    /**
     * POST  /role-operations : Create a new roleOperation.
     *
     * @param roleOperation the roleOperation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleOperation, or with status 400 (Bad Request) if the roleOperation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/role-operations")
    @Timed
    public ResponseEntity<RoleOperation> createRoleOperation(@RequestBody RoleOperation roleOperation) throws URISyntaxException {
        log.debug("REST request to save RoleOperation : {}", roleOperation);
        if (roleOperation.getId() != null) {
            throw new BadRequestAlertException("A new roleOperation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleOperation result = roleOperationRepository.save(roleOperation);
        return ResponseEntity.created(new URI("/api/role-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /role-operations : Updates an existing roleOperation.
     *
     * @param roleOperation the roleOperation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleOperation,
     * or with status 400 (Bad Request) if the roleOperation is not valid,
     * or with status 500 (Internal Server Error) if the roleOperation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/role-operations")
    @Timed
    public ResponseEntity<RoleOperation> updateRoleOperation(@RequestBody RoleOperation roleOperation) throws URISyntaxException {
        log.debug("REST request to update RoleOperation : {}", roleOperation);
        if (roleOperation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleOperation result = roleOperationRepository.save(roleOperation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roleOperation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /role-operations : get all the roleOperations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of roleOperations in body
     */
    @GetMapping("/role-operations")
    @Timed
    public List<RoleOperation> getAllRoleOperations() {
        log.debug("REST request to get all RoleOperations");
        return roleOperationRepository.findAll();
    }

    /**
     * GET  /role-operations/:id : get the "id" roleOperation.
     *
     * @param id the id of the roleOperation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleOperation, or with status 404 (Not Found)
     */
    @GetMapping("/role-operations/{id}")
    @Timed
    public ResponseEntity<RoleOperation> getRoleOperation(@PathVariable Long id) {
        log.debug("REST request to get RoleOperation : {}", id);
        Optional<RoleOperation> roleOperation = roleOperationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(roleOperation);
    }

    /**
     * DELETE  /role-operations/:id : delete the "id" roleOperation.
     *
     * @param id the id of the roleOperation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/role-operations/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoleOperation(@PathVariable Long id) {
        log.debug("REST request to delete RoleOperation : {}", id);

        roleOperationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
