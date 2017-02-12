package box.web.rest;

import box.BachelorApp;

import box.domain.Plant;
import box.repository.PlantRepository;

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
 * Test class for the PlantResource REST controller.
 *
 * @see PlantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BachelorApp.class)
public class PlantResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private PlantRepository plantRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPlantMockMvc;

    private Plant plant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlantResource plantResource = new PlantResource();
        ReflectionTestUtils.setField(plantResource, "plantRepository", plantRepository);
        this.restPlantMockMvc = MockMvcBuilders.standaloneSetup(plantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plant createEntity(EntityManager em) {
        Plant plant = new Plant()
                .name(DEFAULT_NAME);
        return plant;
    }

    @Before
    public void initTest() {
        plant = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlant() throws Exception {
        int databaseSizeBeforeCreate = plantRepository.findAll().size();

        // Create the Plant

        restPlantMockMvc.perform(post("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plant)))
                .andExpect(status().isCreated());

        // Validate the Plant in the database
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(databaseSizeBeforeCreate + 1);
        Plant testPlant = plants.get(plants.size() - 1);
        assertThat(testPlant.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = plantRepository.findAll().size();
        // set the field null
        plant.setName(null);

        // Create the Plant, which fails.

        restPlantMockMvc.perform(post("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plant)))
                .andExpect(status().isBadRequest());

        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlants() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plants
        restPlantMockMvc.perform(get("/api/plants?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(plant.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get the plant
        restPlantMockMvc.perform(get("/api/plants/{id}", plant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlant() throws Exception {
        // Get the plant
        restPlantMockMvc.perform(get("/api/plants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Update the plant
        Plant updatedPlant = plantRepository.findOne(plant.getId());
        updatedPlant
                .name(UPDATED_NAME);

        restPlantMockMvc.perform(put("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPlant)))
                .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(databaseSizeBeforeUpdate);
        Plant testPlant = plants.get(plants.size() - 1);
        assertThat(testPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deletePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);
        int databaseSizeBeforeDelete = plantRepository.findAll().size();

        // Get the plant
        restPlantMockMvc.perform(delete("/api/plants/{id}", plant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
