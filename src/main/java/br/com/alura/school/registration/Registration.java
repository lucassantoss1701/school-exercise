package br.com.alura.school.registration;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Entity
public class Registration {

    @EmbeddedId
    @JsonIgnore
    private RegistrationPK id = new RegistrationPK();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instant;


    public Registration(){}
    public Registration(Course course, User user, Date instant) {
        id.setCourse(course);
        id.setUser(user);
        this.instant = instant;
    }

    public Date getInstant() {
        return instant;
    }

    public Registration setInstant(Date instant) {
        this.instant = instant;
        return this;
    }

    public void setCourse(Course course){
        id.setCourse(course);
    }

    public void setUser(User user){
        id.setUser(user);
    }

    public User getUser(){
        return id.getUser();
    }

    public Course getCourse(){
        return id.getCourse();
    }
}
