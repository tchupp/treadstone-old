package com.treadstone.repository;

import com.treadstone.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Student entity.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findOneByUserId(Long userId);

}
