package box.web.rest;

import box.BachelorApp;

import box.domain.GreenHouse;
import box.repository.GreenHouseRepository;

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
 * Test class for the GreenHouseResource REST controller.
 *
 * @see GreenHouseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BachelorApp.class)
public class GreenHouseResourceIntTest {

    @Inject
    private GreenHouseRepository greenHouseRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGreenHouseMockMvc;

    private GreenHouse greenHouse;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GreenHouseResource greenHouseResource = new GreenHouseResource();
        ReflectionTestUtils.setField(greenHouseResource, "greenHouseRepository", greenHouseRepository);
        this.restGreenHouseMockMvc = MockMvcBuilders.standaloneSetup(greenHouseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GreenHouse createEntity(EntityManager em) {
        GreenHouse greenHouse = new GreenHouse();
        return greenHouse;
    }

    @Before
    public void initTest() {
        greenHouse = createEntity(em);
    }

    @Test
    @Transactional
    public void createGreenHouse() throws Exception {
        int databaseSizeBeforeCreate = greenHouseRepository.findAll().size();

        // Create the GreenHouse

        restGreenHouseMockMvc.perform(post("/api/green-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(greenHouse)))
                .andExpect(status().isCreated());

        // Validate the GreenHouse in the database
        List<GreenHouse> greenHouses = greenHouseRepository.findAll();
        assertThat(greenHouses).hasSize(databaseSizeBeforeCreate + 1);
        GreenHouse testGreenHouse = greenHouses.get(greenHouses.size() - 1);
    }

    @Test
    @Transactional
    public void getAllGreenHouses() throws Exception {
        // Initialize the database
        greenHouseRepository.saveAndFlush(greenHouse);

        // Get all the greenHouses
        restGreenHouseMockMvc.perform(get("/api/green-houses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(greenHouse.getId().intValue())));
    }

    @Test
    @Transactional
    public void getGreenHouse() throws Exception {
        // Initialize the database
        greenHouseRepository.saveAndFlush(greenHouse);

        // Get the greenHouse
        restGreenHouseMockMvc.perform(get("/api/green-houses/{id}", greenHouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(greenHouse.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGreenHouse() throws Exception {
        // Get the greenHouse
        restGreenHouseMockMvc.perform(get("/api/green-houses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGreenHouse() throws Exception {
        // Initialize the database
        greenHouseRepository.saveAndFlush(greenHouse);
        int databaseSizeBeforeUpdate = greenHouseRepository.findAll().size();

        // Update the greenHouse
        GreenHouse updatedGreenHouse = greenHouseRepository.findOne(greenHouse.getId());

        restGreenHouseMockMvc.perform(put("/api/green-houses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGreenHouse)))
                .andExpect(status().isOk());

        // Validate the GreenHouse in the database
        List<GreenHouse> greenHouses = greenHouseRepository.findAll();
        assertThat(greenHouses).hasSize(databaseSizeBeforeUpdate);
        GreenHouse testGreenHouse = greenHouses.get(greenHouses.size() - 1);
    }

    @Test
    @Transactional
    public void deleteGreenHouse() throws Exception {
        // Initialize the database
        greenHouseRepository.saveAndFlush(greenHouse);
        int databaseSizeBeforeDelete = greenHouseRepository.findAll().size();

        // Get the greenHouse
        restGreenHouseMockMvc.perform(delete("/api/green-houses/{id}", greenHouse.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GreenHouse> greenHouses = greenHouseRepository.findAll();
        assertThat(greenHouses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
