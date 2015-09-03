package com.treadstone.repository;

import com.treadstone.domain.Registration;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Registration entity.
 */
public interface RegistrationRepository extends JpaRepository<Registration,Long> {

}
