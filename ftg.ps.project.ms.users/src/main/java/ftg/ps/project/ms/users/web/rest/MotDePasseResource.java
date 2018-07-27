package ftg.ps.project.ms.users.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.users.domain.MotDePasse;
import ftg.ps.project.ms.users.repository.MotDePasseRepository;
import ftg.ps.project.ms.users.web.rest.errors.BadRequestAlertException;
import ftg.ps.project.ms.users.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MotDePasse.
 */
@RestController
@RequestMapping("/api")
public class MotDePasseResource {

    private final Logger log = LoggerFactory.getLogger(MotDePasseResource.class);

    private static final String ENTITY_NAME = "motDePasse";

    private final MotDePasseRepository motDePasseRepository;

    public MotDePasseResource(MotDePasseRepository motDePasseRepository) {
        this.motDePasseRepository = motDePasseRepository;
    }

    /**
     * POST  /mot-de-passes : Create a new motDePasse.
     *
     * @param motDePasse the motDePasse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new motDePasse, or with status 400 (Bad Request) if the motDePasse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mot-de-passes")
    @Timed
    public ResponseEntity<MotDePasse> createMotDePasse(@Valid @RequestBody MotDePasse motDePasse) throws URISyntaxException {
        log.debug("REST request to save MotDePasse : {}", motDePasse);
        if (motDePasse.getId() != null) {
            throw new BadRequestAlertException("A new motDePasse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MotDePasse result = motDePasseRepository.save(motDePasse);
        return ResponseEntity.created(new URI("/api/mot-de-passes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mot-de-passes : Updates an existing motDePasse.
     *
     * @param motDePasse the motDePasse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated motDePasse,
     * or with status 400 (Bad Request) if the motDePasse is not valid,
     * or with status 500 (Internal Server Error) if the motDePasse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mot-de-passes")
    @Timed
    public ResponseEntity<MotDePasse> updateMotDePasse(@Valid @RequestBody MotDePasse motDePasse) throws URISyntaxException {
        log.debug("REST request to update MotDePasse : {}", motDePasse);
        if (motDePasse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MotDePasse result = motDePasseRepository.save(motDePasse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, motDePasse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mot-de-passes : get all the motDePasses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of motDePasses in body
     */
    @GetMapping("/mot-de-passes")
    @Timed
    public List<MotDePasse> getAllMotDePasses() {
        log.debug("REST request to get all MotDePasses");
        return motDePasseRepository.findAll();
    }

    /**
     * GET  /mot-de-passes/:id : get the "id" motDePasse.
     *
     * @param id the id of the motDePasse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the motDePasse, or with status 404 (Not Found)
     */
    @GetMapping("/mot-de-passes/{id}")
    @Timed
    public ResponseEntity<MotDePasse> getMotDePasse(@PathVariable Long id) {
        log.debug("REST request to get MotDePasse : {}", id);
        Optional<MotDePasse> motDePasse = motDePasseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(motDePasse);
    }

    /**
     * DELETE  /mot-de-passes/:id : delete the "id" motDePasse.
     *
     * @param id the id of the motDePasse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mot-de-passes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMotDePasse(@PathVariable Long id) {
        log.debug("REST request to delete MotDePasse : {}", id);

        motDePasseRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
