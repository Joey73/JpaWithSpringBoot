package org.joey.jpa.hibernate.demo.repository;

import javax.persistence.EntityManager;

import org.joey.jpa.hibernate.demo.entity.Course;
import org.joey.jpa.hibernate.demo.entity.Passport;
import org.joey.jpa.hibernate.demo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StudentRepository {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	public Student findById(Long id) {
		return this.em.find(Student.class, id);
	}

	// Insert and update
	public Student save(Student student) {
		if (student.getId() == null) {
			this.em.persist(student);
		} else {
			this.em.merge(student);
		}
		return student;
	}

	public void deleteById(Long id) {
		Student student = this.findById(id);
		this.em.remove(student);
	}

	public void saveStudentWithPassport() {
		Passport passport = new Passport("444");
		this.em.persist(passport);
		Student student = new Student("TestStudent4");
		student.setPassport(passport);
		this.em.persist(student);
	}

	public void insertHardCodedStudentAndCourse() {
		Student student = new Student("TestStudent5");
		Course course = new Course("TestCourse4");
		this.em.persist(student);
		this.em.persist(course);

		// Persist Relationship
		student.addCourse(course);
		course.addStudent(student);

		// Persist Owning Side
		this.em.persist(student);
	}

	public void insertStudentAndCourse(Student student, Course course) {
		// Alternative: Persist at the end
		student.addCourse(course);
		course.addStudent(student);

		this.em.persist(student);
		this.em.persist(course);
	}
}
