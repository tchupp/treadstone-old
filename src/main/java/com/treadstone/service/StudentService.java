package com.treadstone.service;

import com.treadstone.domain.Student;
import com.treadstone.domain.User;
import com.treadstone.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service class for managing students.
 */
@Service
@Transactional
public class StudentService {

    @Inject
    private StudentRepository studentRepository;

    private final Logger log = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(User user) {
        Student student = new Student();
        student.setUser(user);
        student.setStudentId(user.getLogin());

        studentRepository.save(student);
        return student;
    }

}
