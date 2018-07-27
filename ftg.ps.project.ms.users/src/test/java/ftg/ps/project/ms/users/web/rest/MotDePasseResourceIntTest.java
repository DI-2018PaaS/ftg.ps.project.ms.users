package ftg.ps.project.ms.users.web.rest;

import ftg.ps.project.ms.users.UserApp;

import ftg.ps.project.ms.users.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.users.domain.MotDePasse;
import ftg.ps.project.ms.users.repository.MotDePasseRepository;
import ftg.ps.project.ms.users.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static ftg.ps.project.ms.users.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MotDePasseResource REST controller.
 *
 * @see MotDePasseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, UserApp.class})
public class MotDePasseResourceIntTest {

    private static final String DEFAULT_VALEUR_MOT_DE_PASSE = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR_MOT_DE_PASSE = "BBBBBBBBBB";

    private static final Long DEFAULT_MDP_OWNER_USER = 1L;
    private static final Long UPDATED_MDP_OWNER_USER = 2L;

    private static final String DEFAULT_LAST_MOT_DE_PASSE = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MOT_DE_PASSE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MDP_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MDP_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MDP_LAST_CONNECTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MDP_LAST_CONNECTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MDP_LAST_MODIFICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MDP_LAST_MODIFICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MotDePasseRepository motDePasseRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMotDePasseMockMvc;

    private MotDePasse motDePasse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MotDePasseResource motDePasseResource = new MotDePasseResource(motDePasseRepository);
        this.restMotDePasseMockMvc = MockMvcBuilders.standaloneSetup(motDePasseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotDePasse createEntity(EntityManager em) {
        MotDePasse motDePasse = new MotDePasse()
            .valeurMotDePasse(DEFAULT_VALEUR_MOT_DE_PASSE)
            .mdpOwnerUser(DEFAULT_MDP_OWNER_USER)
            .lastMotDePasse(DEFAULT_LAST_MOT_DE_PASSE)
            .mdpCreationDate(DEFAULT_MDP_CREATION_DATE)
            .mdpLastConnectionDate(DEFAULT_MDP_LAST_CONNECTION_DATE)
            .mdpLastModificationDate(DEFAULT_MDP_LAST_MODIFICATION_DATE);
        return motDePasse;
    }

    @Before
    public void initTest() {
        motDePasse = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotDePasse() throws Exception {
        int databaseSizeBeforeCreate = motDePasseRepository.findAll().size();

        // Create the MotDePasse
        restMotDePasseMockMvc.perform(post("/api/mot-de-passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motDePasse)))
            .andExpect(status().isCreated());

        // Validate the MotDePasse in the database
        List<MotDePasse> motDePasseList = motDePasseRepository.findAll();
        assertThat(motDePasseList).hasSize(databaseSizeBeforeCreate + 1);
        MotDePasse testMotDePasse = motDePasseList.get(motDePasseList.size() - 1);
        assertThat(testMotDePasse.getValeurMotDePasse()).isEqualTo(DEFAULT_VALEUR_MOT_DE_PASSE);
        assertThat(testMotDePasse.getMdpOwnerUser()).isEqualTo(DEFAULT_MDP_OWNER_USER);
        assertThat(testMotDePasse.getLastMotDePasse()).isEqualTo(DEFAULT_LAST_MOT_DE_PASSE);
        assertThat(testMotDePasse.getMdpCreationDate()).isEqualTo(DEFAULT_MDP_CREATION_DATE);
        assertThat(testMotDePasse.getMdpLastConnectionDate()).isEqualTo(DEFAULT_MDP_LAST_CONNECTION_DATE);
        assertThat(testMotDePasse.getMdpLastModificationDate()).isEqualTo(DEFAULT_MDP_LAST_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void createMotDePasseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motDePasseRepository.findAll().size();

        // Create the MotDePasse with an existing ID
        motDePasse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotDePasseMockMvc.perform(post("/api/mot-de-passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motDePasse)))
            .andExpect(status().isBadRequest());

        // Validate the MotDePasse in the database
        List<MotDePasse> motDePasseList = motDePasseRepository.findAll();
        assertThat(motDePasseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValeurMotDePasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = motDePasseRepository.findAll().size();
        // set the field null
        motDePasse.setValeurMotDePasse(null);

        // Create the MotDePasse, which fails.

        restMotDePasseMockMvc.perform(post("/api/mot-de-passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motDePasse)))
            .andExpect(status().isBadRequest());

        List<MotDePasse> motDePasseList = motDePasseRepository.findAll();
        assertThat(motDePasseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMdpOwnerUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = motDePasseRepository.findAll().size();
        // set the field null
        motDePasse.setMdpOwnerUser(null);

        // Create the MotDePasse, which fails.

        restMotDePasseMockMvc.perform(post("/api/mot-de-passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motDePasse)))
            .andExpect(status().isBadRequest());

        List<MotDePasse> motDePasseList = motDePasseRepository.findAll();
        assertThat(motDePasseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMotDePasses() throws Exception {
        // Initialize the database
        motDePasseRepository.saveAndFlush(motDePasse);

        // Get all the motDePasseList
        restMotDePasseMockMvc.perform(get("/api/mot-de-passes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motDePasse.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeurMotDePasse").value(hasItem(DEFAULT_VALEUR_MOT_DE_PASSE.toString())))
            .andExpect(jsonPath("$.[*].mdpOwnerUser").value(hasItem(DEFAULT_MDP_OWNER_USER.intValue())))
            .andExpect(jsonPath("$.[*].lastMotDePasse").value(hasItem(DEFAULT_LAST_MOT_DE_PASSE.toString())))
            .andExpect(jsonPath("$.[*].mdpCreationDate").value(hasItem(DEFAULT_MDP_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].mdpLastConnectionDate").value(hasItem(DEFAULT_MDP_LAST_CONNECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].mdpLastModificationDate").value(hasItem(DEFAULT_MDP_LAST_MODIFICATION_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getMotDePasse() throws Exception {
        // Initialize the database
        motDePasseRepository.saveAndFlush(motDePasse);

        // Get the motDePasse
        restMotDePasseMockMvc.perform(get("/api/mot-de-passes/{id}", motDePasse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(motDePasse.getId().intValue()))
            .andExpect(jsonPath("$.valeurMotDePasse").value(DEFAULT_VALEUR_MOT_DE_PASSE.toString()))
            .andExpect(jsonPath("$.mdpOwnerUser").value(DEFAULT_MDP_OWNER_USER.intValue()))
            .andExpect(jsonPath("$.lastMotDePasse").value(DEFAULT_LAST_MOT_DE_PASSE.toString()))
            .andExpect(jsonPath("$.mdpCreationDate").value(DEFAULT_MDP_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.mdpLastConnectionDate").value(DEFAULT_MDP_LAST_CONNECTION_DATE.toString()))
            .andExpect(jsonPath("$.mdpLastModificationDate").value(DEFAULT_MDP_LAST_MODIFICATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMotDePasse() throws Exception {
        // Get the motDePasse
        restMotDePasseMockMvc.perform(get("/api/mot-de-passes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotDePasse() throws Exception {
        // Initialize the database
        motDePasseRepository.saveAndFlush(motDePasse);

        int databaseSizeBeforeUpdate = motDePasseRepository.findAll().size();

        // Update the motDePasse
        MotDePasse updatedMotDePasse = motDePasseRepository.findById(motDePasse.getId()).get();
        // Disconnect from session so that the updates on updatedMotDePasse are not directly saved in db
        em.detach(updatedMotDePasse);
        updatedMotDePasse
            .valeurMotDePasse(UPDATED_VALEUR_MOT_DE_PASSE)
            .mdpOwnerUser(UPDATED_MDP_OWNER_USER)
            .lastMotDePasse(UPDATED_LAST_MOT_DE_PASSE)
            .mdpCreationDate(UPDATED_MDP_CREATION_DATE)
            .mdpLastConnectionDate(UPDATED_MDP_LAST_CONNECTION_DATE)
            .mdpLastModificationDate(UPDATED_MDP_LAST_MODIFICATION_DATE);

        restMotDePasseMockMvc.perform(put("/api/mot-de-passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMotDePasse)))
            .andExpect(status().isOk());

        // Validate the MotDePasse in the database
        List<MotDePasse> motDePasseList = motDePasseRepository.findAll();
        assertThat(motDePasseList).hasSize(databaseSizeBeforeUpdate);
        MotDePasse testMotDePasse = motDePasseList.get(motDePasseList.size() - 1);
        assertThat(testMotDePasse.getValeurMotDePasse()).isEqualTo(UPDATED_VALEUR_MOT_DE_PASSE);
        assertThat(testMotDePasse.getMdpOwnerUser()).isEqualTo(UPDATED_MDP_OWNER_USER);
        assertThat(testMotDePasse.getLastMotDePasse()).isEqualTo(UPDATED_LAST_MOT_DE_PASSE);
        assertThat(testMotDePasse.getMdpCreationDate()).isEqualTo(UPDATED_MDP_CREATION_DATE);
        assertThat(testMotDePasse.getMdpLastConnectionDate()).isEqualTo(UPDATED_MDP_LAST_CONNECTION_DATE);
        assertThat(testMotDePasse.getMdpLastModificationDate()).isEqualTo(UPDATED_MDP_LAST_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMotDePasse() throws Exception {
        int databaseSizeBeforeUpdate = motDePasseRepository.findAll().size();

        // Create the MotDePasse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMotDePasseMockMvc.perform(put("/api/mot-de-passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motDePasse)))
            .andExpect(status().isBadRequest());

        // Validate the MotDePasse in the database
        List<MotDePasse> motDePasseList = motDePasseRepository.findAll();
        assertThat(motDePasseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMotDePasse() throws Exception {
        // Initialize the database
        motDePasseRepository.saveAndFlush(motDePasse);

        int databaseSizeBeforeDelete = motDePasseRepository.findAll().size();

        // Get the motDePasse
        restMotDePasseMockMvc.perform(delete("/api/mot-de-passes/{id}", motDePasse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MotDePasse> motDePasseList = motDePasseRepository.findAll();
        assertThat(motDePasseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotDePasse.class);
        MotDePasse motDePasse1 = new MotDePasse();
        motDePasse1.setId(1L);
        MotDePasse motDePasse2 = new MotDePasse();
        motDePasse2.setId(motDePasse1.getId());
        assertThat(motDePasse1).isEqualTo(motDePasse2);
        motDePasse2.setId(2L);
        assertThat(motDePasse1).isNotEqualTo(motDePasse2);
        motDePasse1.setId(null);
        assertThat(motDePasse1).isNotEqualTo(motDePasse2);
    }
}
