package ftg.ps.project.ms.users.web.rest;

import ftg.ps.project.ms.users.UserApp;

import ftg.ps.project.ms.users.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.users.domain.Utilisateur;
import ftg.ps.project.ms.users.repository.UtilisateurRepository;
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
 * Test class for the UtilisateurResource REST controller.
 *
 * @see UtilisateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, UserApp.class})
public class UtilisateurResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final Long DEFAULT_UTIL_UNIQUEID = 1L;
    private static final Long UPDATED_UTIL_UNIQUEID = 2L;

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE_USER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE_USER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_CONNECTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_CONNECTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_ACTEUR_ID = 1L;
    private static final Long UPDATED_USER_ACTEUR_ID = 2L;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUtilisateurMockMvc;

    private Utilisateur utilisateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UtilisateurResource utilisateurResource = new UtilisateurResource(utilisateurRepository);
        this.restUtilisateurMockMvc = MockMvcBuilders.standaloneSetup(utilisateurResource)
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
    public static Utilisateur createEntity(EntityManager em) {
        Utilisateur utilisateur = new Utilisateur()
            .username(DEFAULT_USERNAME)
            .utilUniqueid(DEFAULT_UTIL_UNIQUEID)
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .mail(DEFAULT_MAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .password(DEFAULT_PASSWORD)
            .creationDateUser(DEFAULT_CREATION_DATE_USER)
            .lastConnectionDate(DEFAULT_LAST_CONNECTION_DATE)
            .userActeurID(DEFAULT_USER_ACTEUR_ID);
        return utilisateur;
    }

    @Before
    public void initTest() {
        utilisateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createUtilisateur() throws Exception {
        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();

        // Create the Utilisateur
        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isCreated());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate + 1);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUtilisateur.getUtilUniqueid()).isEqualTo(DEFAULT_UTIL_UNIQUEID);
        assertThat(testUtilisateur.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUtilisateur.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUtilisateur.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testUtilisateur.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testUtilisateur.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUtilisateur.getCreationDateUser()).isEqualTo(DEFAULT_CREATION_DATE_USER);
        assertThat(testUtilisateur.getLastConnectionDate()).isEqualTo(DEFAULT_LAST_CONNECTION_DATE);
        assertThat(testUtilisateur.getUserActeurID()).isEqualTo(DEFAULT_USER_ACTEUR_ID);
    }

    @Test
    @Transactional
    public void createUtilisateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();

        // Create the Utilisateur with an existing ID
        utilisateur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUtilUniqueidIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setUtilUniqueid(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUtilisateurs() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].utilUniqueid").value(hasItem(DEFAULT_UTIL_UNIQUEID.intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].creationDateUser").value(hasItem(DEFAULT_CREATION_DATE_USER.toString())))
            .andExpect(jsonPath("$.[*].lastConnectionDate").value(hasItem(DEFAULT_LAST_CONNECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].userActeurID").value(hasItem(DEFAULT_USER_ACTEUR_ID.intValue())));
    }
    

    @Test
    @Transactional
    public void getUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get the utilisateur
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/{id}", utilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(utilisateur.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.utilUniqueid").value(DEFAULT_UTIL_UNIQUEID.intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.creationDateUser").value(DEFAULT_CREATION_DATE_USER.toString()))
            .andExpect(jsonPath("$.lastConnectionDate").value(DEFAULT_LAST_CONNECTION_DATE.toString()))
            .andExpect(jsonPath("$.userActeurID").value(DEFAULT_USER_ACTEUR_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUtilisateur() throws Exception {
        // Get the utilisateur
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Update the utilisateur
        Utilisateur updatedUtilisateur = utilisateurRepository.findById(utilisateur.getId()).get();
        // Disconnect from session so that the updates on updatedUtilisateur are not directly saved in db
        em.detach(updatedUtilisateur);
        updatedUtilisateur
            .username(UPDATED_USERNAME)
            .utilUniqueid(UPDATED_UTIL_UNIQUEID)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .mail(UPDATED_MAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .password(UPDATED_PASSWORD)
            .creationDateUser(UPDATED_CREATION_DATE_USER)
            .lastConnectionDate(UPDATED_LAST_CONNECTION_DATE)
            .userActeurID(UPDATED_USER_ACTEUR_ID);

        restUtilisateurMockMvc.perform(put("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUtilisateur)))
            .andExpect(status().isOk());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUtilisateur.getUtilUniqueid()).isEqualTo(UPDATED_UTIL_UNIQUEID);
        assertThat(testUtilisateur.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUtilisateur.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUtilisateur.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testUtilisateur.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUtilisateur.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUtilisateur.getCreationDateUser()).isEqualTo(UPDATED_CREATION_DATE_USER);
        assertThat(testUtilisateur.getLastConnectionDate()).isEqualTo(UPDATED_LAST_CONNECTION_DATE);
        assertThat(testUtilisateur.getUserActeurID()).isEqualTo(UPDATED_USER_ACTEUR_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Create the Utilisateur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUtilisateurMockMvc.perform(put("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeDelete = utilisateurRepository.findAll().size();

        // Get the utilisateur
        restUtilisateurMockMvc.perform(delete("/api/utilisateurs/{id}", utilisateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utilisateur.class);
        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setId(1L);
        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setId(utilisateur1.getId());
        assertThat(utilisateur1).isEqualTo(utilisateur2);
        utilisateur2.setId(2L);
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
        utilisateur1.setId(null);
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
    }
}
