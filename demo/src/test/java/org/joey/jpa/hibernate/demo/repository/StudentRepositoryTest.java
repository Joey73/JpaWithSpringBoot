package org.joey.jpa.hibernate.demo.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.joey.jpa.hibernate.demo.DemoApplication;
import org.joey.jpa.hibernate.demo.entity.Address;
import org.joey.jpa.hibernate.demo.entity.Passport;
import org.joey.jpa.hibernate.demo.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class StudentRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	StudentRepository repository;

	@Autowired
	EntityManager em;

	@Test
	@Transactional
	public void someTest() {
		// DB Operation 1: Retrieve student
		Student student = this.em.find(Student.class, 20001L);
		// Persistence Context(student)

		// DB Operation 2: Retrieve passport
		Passport passport = student.getPassport();
		// Persistence Context(student, passport)

		// DB Operation 3: Update passport
		passport.setNumber("111 updated");
		// Persistence Context(student, passport++)

		// DB Operation 4: Update student
		student.setName("Student1 - updated");
		// Persistence Context(student++, passport++)
	}

	@Test
	@Transactional
	public void retrieveStudentAndPassportDetails() {
		Student student = this.em.find(Student.class, 20001L);
		this.logger.info("Student: {}", student);
		this.logger.info("Passport: {}", student.getPassport());
	}

	@Test
	@Transactional
	public void setAddressDetails() {
		Student student = this.em.find(Student.class, 20001L);
		student.setAddress(new Address("123", "Some street", "Cologne"));
		this.em.flush();
		this.logger.info("Student: {}", student);
		this.logger.info("Passport: {}", student.getPassport());
	}

	@Test
	@Transactional
	public void retrievePassportAndAssociatedStudent() {
		Passport passport = this.em.find(Passport.class, 40001L);
		this.logger.info("Passport: {}", passport);
		this.logger.info("Student: {}", passport.getStudent());
	}

	@Test
	@Transactional
	public void retrieveStudentAndCourses() {
		Student student = this.em.find(Student.class, 20001L);
		this.logger.info("student: {}", student);
		this.logger.info("courses: {}", student.getCourses());
	}
}
