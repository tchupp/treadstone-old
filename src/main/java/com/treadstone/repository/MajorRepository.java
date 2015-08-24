package com.treadstone.repository;

import com.treadstone.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Major entity.
 */
public interface MajorRepository extends JpaRepository<Major, Long> {

}
