package box.web.rest;

import box.BachelorApp;

import box.domain.ProfileSettings;
import box.repository.ProfileSettingsRepository;

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
 * Test class for the ProfileSettingsResource REST controller.
 *
 * @see ProfileSettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BachelorApp.class)
public class ProfileSettingsResourceIntTest {

    private static final Double DEFAULT_MAX_GROUND_HUMIDITY = 1D;
    private static final Double UPDATED_MAX_GROUND_HUMIDITY = 2D;

    private static final Double DEFAULT_MIN_GROUN_HUMIDITY = 1D;
    private static final Double UPDATED_MIN_GROUN_HUMIDITY = 2D;

    private static final Double DEFAULT_MIN_HUMIDITY = 1D;
    private static final Double UPDATED_MIN_HUMIDITY = 2D;

    private static final Double DEFAULT_MAX_HUMIDITY = 1D;
    private static final Double UPDATED_MAX_HUMIDITY = 2D;

    private static final Double DEFAULT_MAX_TEMPERATURE = 1D;
    private static final Double UPDATED_MAX_TEMPERATURE = 2D;

    private static final Double DEFAULT_MIN_TEMPERATURE = 1D;
    private static final Double UPDATED_MIN_TEMPERATURE = 2D;

    private static final Integer DEFAULT_START_HOUR = 1;
    private static final Integer UPDATED_START_HOUR = 2;

    private static final Integer DEFAULT_START_MINUTE = 1;
    private static final Integer UPDATED_START_MINUTE = 2;

    private static final Integer DEFAULT_END_MINUTE = 1;
    private static final Integer UPDATED_END_MINUTE = 2;

    private static final Integer DEFAULT_END_HOUR = 1;
    private static final Integer UPDATED_END_HOUR = 2;

    @Inject
    private ProfileSettingsRepository profileSettingsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProfileSettingsMockMvc;

    private ProfileSettings profileSettings;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfileSettingsResource profileSettingsResource = new ProfileSettingsResource();
        ReflectionTestUtils.setField(profileSettingsResource, "profileSettingsRepository", profileSettingsRepository);
        this.restProfileSettingsMockMvc = MockMvcBuilders.standaloneSetup(profileSettingsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileSettings createEntity(EntityManager em) {
        ProfileSettings profileSettings = new ProfileSettings()
                .maxGroundHumidity(DEFAULT_MAX_GROUND_HUMIDITY)
                .minGrounHumidity(DEFAULT_MIN_GROUN_HUMIDITY)
                .minHumidity(DEFAULT_MIN_HUMIDITY)
                .maxHumidity(DEFAULT_MAX_HUMIDITY)
                .maxTemperature(DEFAULT_MAX_TEMPERATURE)
                .minTemperature(DEFAULT_MIN_TEMPERATURE)
                .startHour(DEFAULT_START_HOUR)
                .startMinute(DEFAULT_START_MINUTE)
                .endMinute(DEFAULT_END_MINUTE)
                .endHour(DEFAULT_END_HOUR);
        return profileSettings;
    }

    @Before
    public void initTest() {
        profileSettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileSettings() throws Exception {
        int databaseSizeBeforeCreate = profileSettingsRepository.findAll().size();

        // Create the ProfileSettings

        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profileSettings)))
                .andExpect(status().isCreated());

        // Validate the ProfileSettings in the database
        List<ProfileSettings> profileSettings = profileSettingsRepository.findAll();
        assertThat(profileSettings).hasSize(databaseSizeBeforeCreate + 1);
        ProfileSettings testProfileSettings = profileSettings.get(profileSettings.size() - 1);
        assertThat(testProfileSettings.getMaxGroundHumidity()).isEqualTo(DEFAULT_MAX_GROUND_HUMIDITY);
        assertThat(testProfileSettings.getMinGrounHumidity()).isEqualTo(DEFAULT_MIN_GROUN_HUMIDITY);
        assertThat(testProfileSettings.getMinHumidity()).isEqualTo(DEFAULT_MIN_HUMIDITY);
        assertThat(testProfileSettings.getMaxHumidity()).isEqualTo(DEFAULT_MAX_HUMIDITY);
        assertThat(testProfileSettings.getMaxTemperature()).isEqualTo(DEFAULT_MAX_TEMPERATURE);
        assertThat(testProfileSettings.getMinTemperature()).isEqualTo(DEFAULT_MIN_TEMPERATURE);
        assertThat(testProfileSettings.getStartHour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testProfileSettings.getStartMinute()).isEqualTo(DEFAULT_START_MINUTE);
        assertThat(testProfileSettings.getEndMinute()).isEqualTo(DEFAULT_END_MINUTE);
        assertThat(testProfileSettings.getEndHour()).isEqualTo(DEFAULT_END_HOUR);
    }

    @Test
    @Transactional
    public void checkMaxGroundHumidityIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSettingsRepository.findAll().size();
        // set the field null
        profileSettings.setMaxGroundHumidity(null);

        // Create the ProfileSettings, which fails.

        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profileSettings)))
                .andExpect(status().isBadRequest());

        List<ProfileSettings> profileSettings = profileSettingsRepository.findAll();
        assertThat(profileSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinGrounHumidityIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSettingsRepository.findAll().size();
        // set the field null
        profileSettings.setMinGrounHumidity(null);

        // Create the ProfileSettings, which fails.

        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profileSettings)))
                .andExpect(status().isBadRequest());

        List<ProfileSettings> profileSettings = profileSettingsRepository.findAll();
        assertThat(profileSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinHumidityIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSettingsRepository.findAll().size();
        // set the field null
        profileSettings.setMinHumidity(null);

        // Create the ProfileSettings, which fails.

        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profileSettings)))
                .andExpect(status().isBadRequest());

        List<ProfileSettings> profileSettings = profileSettingsRepository.findAll();
        assertThat(profileSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxHumidityIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSettingsRepository.findAll().size();
        // set the field null
        profileSettings.setMaxHumidity(null);

        // Create the ProfileSettings, which fails.

        restProfileSettingsMockMvc.perform(post("/api/profile-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profileSettings)))
                .andExpect(status().isBadRequest());

        List<ProfileSettings> profileSettings = profileSettingsRepository.findAll();
        assertThat(profileSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfileSettings() throws Exception {
        // Initialize the database
        profileSettingsRepository.saveAndFlush(profileSettings);

        // Get all the profileSettings
        restProfileSettingsMockMvc.perform(get("/api/profile-settings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(profileSettings.getId().intValue())))
                .andExpect(jsonPath("$.[*].maxGroundHumidity").value(hasItem(DEFAULT_MAX_GROUND_HUMIDITY.doubleValue())))
                .andExpect(jsonPath("$.[*].minGrounHumidity").value(hasItem(DEFAULT_MIN_GROUN_HUMIDITY.doubleValue())))
                .andExpect(jsonPath("$.[*].minHumidity").value(hasItem(DEFAULT_MIN_HUMIDITY.doubleValue())))
                .andExpect(jsonPath("$.[*].maxHumidity").value(hasItem(DEFAULT_MAX_HUMIDITY.doubleValue())))
                .andExpect(jsonPath("$.[*].maxTemperature").value(hasItem(DEFAULT_MAX_TEMPERATURE.doubleValue())))
                .andExpect(jsonPath("$.[*].minTemperature").value(hasItem(DEFAULT_MIN_TEMPERATURE.doubleValue())))
                .andExpect(jsonPath("$.[*].startHour").value(hasItem(DEFAULT_START_HOUR)))
                .andExpect(jsonPath("$.[*].startMinute").value(hasItem(DEFAULT_START_MINUTE)))
                .andExpect(jsonPath("$.[*].endMinute").value(hasItem(DEFAULT_END_MINUTE)))
                .andExpect(jsonPath("$.[*].endHour").value(hasItem(DEFAULT_END_HOUR)));
    }

    @Test
    @Transactional
    public void getProfileSettings() throws Exception {
        // Initialize the database
        profileSettingsRepository.saveAndFlush(profileSettings);

        // Get the profileSettings
        restProfileSettingsMockMvc.perform(get("/api/profile-settings/{id}", profileSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileSettings.getId().intValue()))
            .andExpect(jsonPath("$.maxGroundHumidity").value(DEFAULT_MAX_GROUND_HUMIDITY.doubleValue()))
            .andExpect(jsonPath("$.minGrounHumidity").value(DEFAULT_MIN_GROUN_HUMIDITY.doubleValue()))
            .andExpect(jsonPath("$.minHumidity").value(DEFAULT_MIN_HUMIDITY.doubleValue()))
            .andExpect(jsonPath("$.maxHumidity").value(DEFAULT_MAX_HUMIDITY.doubleValue()))
            .andExpect(jsonPath("$.maxTemperature").value(DEFAULT_MAX_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.minTemperature").value(DEFAULT_MIN_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.startHour").value(DEFAULT_START_HOUR))
            .andExpect(jsonPath("$.startMinute").value(DEFAULT_START_MINUTE))
            .andExpect(jsonPath("$.endMinute").value(DEFAULT_END_MINUTE))
            .andExpect(jsonPath("$.endHour").value(DEFAULT_END_HOUR));
    }

    @Test
    @Transactional
    public void getNonExistingProfileSettings() throws Exception {
        // Get the profileSettings
        restProfileSettingsMockMvc.perform(get("/api/profile-settings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileSettings() throws Exception {
        // Initialize the database
        profileSettingsRepository.saveAndFlush(profileSettings);
        int databaseSizeBeforeUpdate = profileSettingsRepository.findAll().size();

        // Update the profileSettings
        ProfileSettings updatedProfileSettings = profileSettingsRepository.findOne(profileSettings.getId());
        updatedProfileSettings
                .maxGroundHumidity(UPDATED_MAX_GROUND_HUMIDITY)
                .minGrounHumidity(UPDATED_MIN_GROUN_HUMIDITY)
                .minHumidity(UPDATED_MIN_HUMIDITY)
                .maxHumidity(UPDATED_MAX_HUMIDITY)
                .maxTemperature(UPDATED_MAX_TEMPERATURE)
                .minTemperature(UPDATED_MIN_TEMPERATURE)
                .startHour(UPDATED_START_HOUR)
                .startMinute(UPDATED_START_MINUTE)
                .endMinute(UPDATED_END_MINUTE)
                .endHour(UPDATED_END_HOUR);

        restProfileSettingsMockMvc.perform(put("/api/profile-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProfileSettings)))
                .andExpect(status().isOk());

        // Validate the ProfileSettings in the database
        List<ProfileSettings> profileSettings = profileSettingsRepository.findAll();
        assertThat(profileSettings).hasSize(databaseSizeBeforeUpdate);
        ProfileSettings testProfileSettings = profileSettings.get(profileSettings.size() - 1);
        assertThat(testProfileSettings.getMaxGroundHumidity()).isEqualTo(UPDATED_MAX_GROUND_HUMIDITY);
        assertThat(testProfileSettings.getMinGrounHumidity()).isEqualTo(UPDATED_MIN_GROUN_HUMIDITY);
        assertThat(testProfileSettings.getMinHumidity()).isEqualTo(UPDATED_MIN_HUMIDITY);
        assertThat(testProfileSettings.getMaxHumidity()).isEqualTo(UPDATED_MAX_HUMIDITY);
        assertThat(testProfileSettings.getMaxTemperature()).isEqualTo(UPDATED_MAX_TEMPERATURE);
        assertThat(testProfileSettings.getMinTemperature()).isEqualTo(UPDATED_MIN_TEMPERATURE);
        assertThat(testProfileSettings.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testProfileSettings.getStartMinute()).isEqualTo(UPDATED_START_MINUTE);
        assertThat(testProfileSettings.getEndMinute()).isEqualTo(UPDATED_END_MINUTE);
        assertThat(testProfileSettings.getEndHour()).isEqualTo(UPDATED_END_HOUR);
    }

    @Test
    @Transactional
    public void deleteProfileSettings() throws Exception {
        // Initialize the database
        profileSettingsRepository.saveAndFlush(profileSettings);
        int databaseSizeBeforeDelete = profileSettingsRepository.findAll().size();

        // Get the profileSettings
        restProfileSettingsMockMvc.perform(delete("/api/profile-settings/{id}", profileSettings.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfileSettings> profileSettings = profileSettingsRepository.findAll();
        assertThat(profileSettings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
