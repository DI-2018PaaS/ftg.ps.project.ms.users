package ftg.ps.project.ms.users.web.rest;

import ftg.ps.project.ms.users.UserApp;

import ftg.ps.project.ms.users.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.users.domain.RoleOperation;
import ftg.ps.project.ms.users.repository.RoleOperationRepository;
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
import java.util.List;


import static ftg.ps.project.ms.users.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RoleOperationResource REST controller.
 *
 * @see RoleOperationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, UserApp.class})
public class RoleOperationResourceIntTest {

    @Autowired
    private RoleOperationRepository roleOperationRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRoleOperationMockMvc;

    private RoleOperation roleOperation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleOperationResource roleOperationResource = new RoleOperationResource(roleOperationRepository);
        this.restRoleOperationMockMvc = MockMvcBuilders.standaloneSetup(roleOperationResource)
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
    public static RoleOperation createEntity(EntityManager em) {
        RoleOperation roleOperation = new RoleOperation();
        return roleOperation;
    }

    @Before
    public void initTest() {
        roleOperation = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoleOperation() throws Exception {
        int databaseSizeBeforeCreate = roleOperationRepository.findAll().size();

        // Create the RoleOperation
        restRoleOperationMockMvc.perform(post("/api/role-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleOperation)))
            .andExpect(status().isCreated());

        // Validate the RoleOperation in the database
        List<RoleOperation> roleOperationList = roleOperationRepository.findAll();
        assertThat(roleOperationList).hasSize(databaseSizeBeforeCreate + 1);
        RoleOperation testRoleOperation = roleOperationList.get(roleOperationList.size() - 1);
    }

    @Test
    @Transactional
    public void createRoleOperationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleOperationRepository.findAll().size();

        // Create the RoleOperation with an existing ID
        roleOperation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleOperationMockMvc.perform(post("/api/role-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleOperation)))
            .andExpect(status().isBadRequest());

        // Validate the RoleOperation in the database
        List<RoleOperation> roleOperationList = roleOperationRepository.findAll();
        assertThat(roleOperationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRoleOperations() throws Exception {
        // Initialize the database
        roleOperationRepository.saveAndFlush(roleOperation);

        // Get all the roleOperationList
        restRoleOperationMockMvc.perform(get("/api/role-operations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleOperation.getId().intValue())));
    }
    

    @Test
    @Transactional
    public void getRoleOperation() throws Exception {
        // Initialize the database
        roleOperationRepository.saveAndFlush(roleOperation);

        // Get the roleOperation
        restRoleOperationMockMvc.perform(get("/api/role-operations/{id}", roleOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roleOperation.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingRoleOperation() throws Exception {
        // Get the roleOperation
        restRoleOperationMockMvc.perform(get("/api/role-operations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoleOperation() throws Exception {
        // Initialize the database
        roleOperationRepository.saveAndFlush(roleOperation);

        int databaseSizeBeforeUpdate = roleOperationRepository.findAll().size();

        // Update the roleOperation
        RoleOperation updatedRoleOperation = roleOperationRepository.findById(roleOperation.getId()).get();
        // Disconnect from session so that the updates on updatedRoleOperation are not directly saved in db
        em.detach(updatedRoleOperation);

        restRoleOperationMockMvc.perform(put("/api/role-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoleOperation)))
            .andExpect(status().isOk());

        // Validate the RoleOperation in the database
        List<RoleOperation> roleOperationList = roleOperationRepository.findAll();
        assertThat(roleOperationList).hasSize(databaseSizeBeforeUpdate);
        RoleOperation testRoleOperation = roleOperationList.get(roleOperationList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingRoleOperation() throws Exception {
        int databaseSizeBeforeUpdate = roleOperationRepository.findAll().size();

        // Create the RoleOperation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoleOperationMockMvc.perform(put("/api/role-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleOperation)))
            .andExpect(status().isBadRequest());

        // Validate the RoleOperation in the database
        List<RoleOperation> roleOperationList = roleOperationRepository.findAll();
        assertThat(roleOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoleOperation() throws Exception {
        // Initialize the database
        roleOperationRepository.saveAndFlush(roleOperation);

        int databaseSizeBeforeDelete = roleOperationRepository.findAll().size();

        // Get the roleOperation
        restRoleOperationMockMvc.perform(delete("/api/role-operations/{id}", roleOperation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoleOperation> roleOperationList = roleOperationRepository.findAll();
        assertThat(roleOperationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleOperation.class);
        RoleOperation roleOperation1 = new RoleOperation();
        roleOperation1.setId(1L);
        RoleOperation roleOperation2 = new RoleOperation();
        roleOperation2.setId(roleOperation1.getId());
        assertThat(roleOperation1).isEqualTo(roleOperation2);
        roleOperation2.setId(2L);
        assertThat(roleOperation1).isNotEqualTo(roleOperation2);
        roleOperation1.setId(null);
        assertThat(roleOperation1).isNotEqualTo(roleOperation2);
    }
}
