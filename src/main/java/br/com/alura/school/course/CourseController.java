package br.com.alura.school.course;

import br.com.alura.school.course.exceptions.DataIntegrityException;
import br.com.alura.school.course.exceptions.ObjectNotFoundException;
import br.com.alura.school.registration.NewRegistration;
import br.com.alura.school.registration.Registration;
import br.com.alura.school.registration.RegistrationRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
class CourseController {

    private static final Logger LOG= LoggerFactory.getLogger(CourseController.class);

    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;


    CourseController(CourseRepository courseRepository, RegistrationRepository registrationRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/courses")
    ResponseEntity<List<CourseResponse>> allCourses() {
        List<Course> listCourse = courseRepository.findAll();
        List<CourseResponse> listCourseResponse = listCourse.stream().map(CourseResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listCourseResponse);
    }

    @GetMapping("/courses/{code}")
    ResponseEntity<CourseResponse> courseByCode(@PathVariable("code") String code) {
        Course course = courseRepository.findByCode(code).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Course with code %s not found", code)));
        return ResponseEntity.ok(new CourseResponse(course));
    }

    @PostMapping("/courses")
    ResponseEntity<Void> newCourse(@RequestBody @Valid NewCourseRequest newCourseRequest) {
        courseRepository.save(newCourseRequest.toEntity());
        URI location = URI.create(format("/courses/%s", newCourseRequest.getCode()));
        return ResponseEntity.created(location).build();
    }
}
