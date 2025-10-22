package com.wecp.progressive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecp.progressive.entity.Clinic;
import com.wecp.progressive.entity.Doctor;
import com.wecp.progressive.entity.Patient;
import com.wecp.progressive.entity.User;
import com.wecp.progressive.jwt.JwtUtil;
import com.wecp.progressive.repository.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TwelveThirteenTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String secret = "secretKey000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

    @BeforeEach
    void setup() {
        appointmentRepository.deleteAll();
        billingRepository.deleteAll();
        clinicRepository.deleteAll();
        userRepository.deleteAll();
        String url = "jdbc:mysql://localhost:3306/mediconnect_test_wecp";
        String user = "root";
        String password = "password";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            String deleteQuery = "DELETE FROM patient";
            statement.executeUpdate(deleteQuery);

            deleteQuery = "DELETE FROM doctor";
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Patient getPatientObject(Integer id, String patientName) throws ParseException {
        Patient patient = new Patient();
        if (id != null) {
            patient.setPatientId(id.intValue());
        }
        patient.setFullName(patientName);
        patient.setContactNumber("9876543210");
        patient.setEmail("mary@gmail.com");
        patient.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1985-06-15"));
        return patient;
    }

    Doctor getDoctorObject(Integer id, String name, int experience) {
        Doctor doctor = new Doctor();
        if (id != null) {
            doctor.setDoctorId(id.intValue());
        }
        doctor.setFullName(name);
        doctor.setContactNumber("9876543210");
        doctor.setEmail("john@gmail.com");
        doctor.setSpecialty("Orthopedic");
        doctor.setYearsOfExperience(experience);
        return doctor;
    }

    Clinic getClinicObject(Integer id, Doctor doctor, String name) {
        Clinic clinic = new Clinic();
        if (id != null) {
            clinic.setClinicId(id);
        }
        setDynamicProperty(clinic, "doctor", doctor);
        clinic.setLocation("Austin");
        clinic.setClinicName(name);
        clinic.setContactNumber("9876543210");
        clinic.setEstablishedYear(2020);
        return clinic;
    }

    public void setDynamicProperty(Object entity, String propertyName, Object value) {
        try {
            Field field = entity.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (Exception e) {
            // Handle exception
        }
    }

    @Test
    void testGenerateToken_Day12() {
        User user = new User();
        user.setUsername("testUser");
        user.setUserId(1);
        user.setRole("PATIENT");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        String token = jwtUtil.generateToken("testUser");

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        assertEquals("PATIENT", claims.get("role"));
    }

    @Test
    void testValidateToken_Day12() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("123");
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userId", 123);
        claimsMap.put("role", "PATIENT");

        String token = Jwts.builder()
                .setClaims(claimsMap)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
                .compact();

        boolean isValid = jwtUtil.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    @WithMockUser(authorities = {"PATIENT"})
    void testGetPatient_Day13() throws Exception {
        Patient patient = getPatientObject(null, "John");
        int id = patientRepository.save(patient).getPatientId();
        mockMvc.perform(get("/patient/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(authorities = {"PATIENT"})
    void testUnauthorisedAddDoctor_Day13() throws Exception {
        mockMvc.perform(post("/doctor"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"DOCTOR"})
    public void testDoctorControllerAddDoctor_Day13() throws Exception {
        Doctor doctor = getDoctorObject(null, "john", 10);
        mockMvc.perform(post("/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertEquals(1, doctorList.size());
        assertEquals("john", doctorList.get(0).getFullName());
    }

    @Test
    @WithMockUser(authorities = {"DOCTOR"})
    public void testAddClinic_Day13() throws Exception {
        Doctor doctor1 = getDoctorObject(1, "John", 10);
        doctor1.setDoctorId(doctorRepository.save(doctor1).getDoctorId());

        Clinic clinic1 = getClinicObject(null, doctor1, "Derma clinic");
        mockMvc.perform(post("/clinic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clinic1)))
                .andExpect(status().isCreated());

        List<Clinic> clinicList = clinicRepository.findAll();
        assertEquals(1, clinicList.size());
        assertEquals("Derma clinic", clinicList.get(0).getClinicName());
    }

}
