package box.web.rest;

import box.BachelorApp;

import box.domain.OutSwitch;
import box.repository.OutSwitchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OutSwitchResource REST controller.
 *
 * @see OutSwitchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BachelorApp.class)
public class OutSwitchResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_PIN_NUMBER = 1;
    private static final Integer UPDATED_PIN_NUMBER = 2;

    @Inject
    private OutSwitchRepository outSwitchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOutSwitchMockMvc;

    private OutSwitch outSwitch;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OutSwitchResource outSwitchResource = new OutSwitchResource();
        ReflectionTestUtils.setField(outSwitchResource, "outSwitchRepository", outSwitchRepository);
        this.restOutSwitchMockMvc = MockMvcBuilders.standaloneSetup(outSwitchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OutSwitch createEntity(EntityManager em) {
        OutSwitch outSwitch = new OutSwitch()
                .name(DEFAULT_NAME)
                .pinNumber(DEFAULT_PIN_NUMBER);
        return outSwitch;
    }

    @Before
    public void initTest() {
        outSwitch = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutSwitch() throws Exception {
        int databaseSizeBeforeCreate = outSwitchRepository.findAll().size();

        // Create the OutSwitch

        restOutSwitchMockMvc.perform(post("/api/out-switches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(outSwitch)))
                .andExpect(status().isCreated());

        // Validate the OutSwitch in the database
        List<OutSwitch> outSwitches = outSwitchRepository.findAll();
        assertThat(outSwitches).hasSize(databaseSizeBeforeCreate + 1);
        OutSwitch testOutSwitch = outSwitches.get(outSwitches.size() - 1);
        assertThat(testOutSwitch.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOutSwitch.getPinNumber()).isEqualTo(DEFAULT_PIN_NUMBER);
    }

    @Test
    @Transactional
    public void checkPinNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = outSwitchRepository.findAll().size();
        // set the field null
        outSwitch.setPinNumber(null);

        // Create the OutSwitch, which fails.

        restOutSwitchMockMvc.perform(post("/api/out-switches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(outSwitch)))
                .andExpect(status().isBadRequest());

        List<OutSwitch> outSwitches = outSwitchRepository.findAll();
        assertThat(outSwitches).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOutSwitches() throws Exception {
        // Initialize the database
        outSwitchRepository.saveAndFlush(outSwitch);

        // Get all the outSwitches
        restOutSwitchMockMvc.perform(get("/api/out-switches?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(outSwitch.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].pinNumber").value(hasItem(DEFAULT_PIN_NUMBER)));
    }

    @Test
    @Transactional
    public void getOutSwitch() throws Exception {
        // Initialize the database
        outSwitchRepository.saveAndFlush(outSwitch);

        // Get the outSwitch
        restOutSwitchMockMvc.perform(get("/api/out-switches/{id}", outSwitch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(outSwitch.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.pinNumber").value(DEFAULT_PIN_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingOutSwitch() throws Exception {
        // Get the outSwitch
        restOutSwitchMockMvc.perform(get("/api/out-switches/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutSwitch() throws Exception {
        // Initialize the database
        outSwitchRepository.saveAndFlush(outSwitch);
        int databaseSizeBeforeUpdate = outSwitchRepository.findAll().size();

        // Update the outSwitch
        OutSwitch updatedOutSwitch = outSwitchRepository.findOne(outSwitch.getId());
        updatedOutSwitch
                .name(UPDATED_NAME)
                .pinNumber(UPDATED_PIN_NUMBER);

        restOutSwitchMockMvc.perform(put("/api/out-switches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedOutSwitch)))
                .andExpect(status().isOk());

        // Validate the OutSwitch in the database
        List<OutSwitch> outSwitches = outSwitchRepository.findAll();
        assertThat(outSwitches).hasSize(databaseSizeBeforeUpdate);
        OutSwitch testOutSwitch = outSwitches.get(outSwitches.size() - 1);
        assertThat(testOutSwitch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOutSwitch.getPinNumber()).isEqualTo(UPDATED_PIN_NUMBER);
    }

    @Test
    @Transactional
    public void deleteOutSwitch() throws Exception {
        // Initialize the database
        outSwitchRepository.saveAndFlush(outSwitch);
        int databaseSizeBeforeDelete = outSwitchRepository.findAll().size();

        // Get the outSwitch
        restOutSwitchMockMvc.perform(delete("/api/out-switches/{id}", outSwitch.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OutSwitch> outSwitches = outSwitchRepository.findAll();
        assertThat(outSwitches).hasSize(databaseSizeBeforeDelete - 1);
    }
}
