package com.treadstone.service;

import com.treadstone.Application;
import com.treadstone.domain.Student;
import com.treadstone.domain.User;
import com.treadstone.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Test class for the StudentResource REST controller.
 *
 * @see StudentService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class StudentServiceTest {

    @Mock
    private StudentRepository mockStudentRepository;

    private StudentService studentService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        studentService = new StudentService();

        ReflectionTestUtils.setField(studentService, "studentRepository", mockStudentRepository);
    }

    @Test
    public void testCreateStudentWithUserInformation() throws Exception {
        String expectedLogin = "chupacabra";
        User user = new User();
        user.setLogin(expectedLogin);

        Student student = studentService.createStudent(user);

        assertThat(student.getUser()).isSameAs(user);
        assertThat(student.getStudentId()).isEqualTo(expectedLogin);

        verify(mockStudentRepository).save(student);
    }
}
