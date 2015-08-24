package com.treadstone.repository;

import com.treadstone.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Course entity.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

}
