package br.com.alura.school.registration;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.course.NewCourseRequest;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RegistrationControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private RegistrationRepository registrationRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CourseRepository courseRepositoryMock;

    @Test
    void should_add_new_course_with_user() throws Exception {
        NewRegistration newRegistration = new NewRegistration("Lucas");
        NewCourseRequest newCourseRequest = new NewCourseRequest("java-2", "Java Collections", "Java Collections: Lists, Sets, Maps and more.");
        User user = new User("Lucas", "lucas@gmail.com");
        Course course = new Course("java-2", "java", "Curso de java");
        Registration registration = new Registration(course, user, new Date());

        Optional<User> userLucas = Optional.of(user);
        Optional<Course> course1 = Optional.of(course);

        when(registrationRepository.save(eq(registration))).thenReturn(registration);
        when(userRepository.findByUsername(eq("Lucas"))).thenReturn(userLucas);
        when(courseRepositoryMock.findByCode(eq("java-2"))).thenReturn(course1);


        mockMvc.perform(post("/courses/java-2/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newRegistration)))
                .andExpect(status().isCreated());
    }
}
