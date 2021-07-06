package br.com.alura.school.registration;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.course.exceptions.DataIntegrityException;
import br.com.alura.school.course.exceptions.ObjectNotFoundException;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RestController
public class RegistrationController {

    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    public RegistrationController(CourseRepository courseRepository, RegistrationRepository registrationRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/courses/{code}/enroll")
    ResponseEntity<Void> newCourseWhitAluno(@RequestBody @Valid NewRegistration newRegistration, @PathVariable("code") String code) {
        Registration registration = new Registration();
        User user = findUserByUsername(newRegistration.getUsername());
        Course course = findCourseByCode(code);
        verifyRegistration(registrationRepository.findAll(), user, course);
        registration.setCourse(course);
        registration.setUser(user);
        registration.setInstant(new Date());
        registrationRepository.save(registration);

        URI location = URI.create(format("/courses/%s", registration.getCourse().getId(), registration.getUser().getId()));

        return ResponseEntity.created(location).build();
    }

    public Course findCourseByCode(String code){
        Optional<Course> course = courseRepository.findByCode(code);
        return course.orElseThrow(()-> new ObjectNotFoundException(
                "Curso não encontrado! Id: "+code+ ", Tipo: "+ Course.class.getName()));
    }

    public User findUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);

        return user.orElseThrow(()-> new ObjectNotFoundException(
                "Usuário não encontrado! Username: "+username+ ", Tipo: "+ Course.class.getName()));
    }

    public void verifyRegistration(List<Registration> resultRegistration, User user, Course course) {
        if (!resultRegistration.isEmpty()) {
            for (Registration res : resultRegistration) {
                if (res.getUser().getId().equals(user.getId()) && res.getCourse().getId().equals(course.getId())) {
                    throw new DataIntegrityException("Usuário já se encontra cadastrado nesse curso!");
                }
            }
        }
    }
}
