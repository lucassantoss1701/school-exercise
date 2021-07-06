package br.com.alura.school.registration;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
}
