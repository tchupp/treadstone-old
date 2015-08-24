package com.treadstone.repository;

import com.treadstone.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Semester entity.
 */
public interface SemesterRepository extends JpaRepository<Semester, Long> {

}
