package com.treadstone.repository;

import com.treadstone.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Student entity.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

}
