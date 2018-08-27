package org.joey.jpa.hibernate.demo;

import org.joey.jpa.hibernate.demo.repository.CourseRepository;
import org.joey.jpa.hibernate.demo.repository.EmployeeRepository;
import org.joey.jpa.hibernate.demo.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Course course = repository.findById(10001L);
		// logger.info("Course 10001: {}", course);
		// this.courseRepository.playWithEntityManger();
		// this.studentRepository.saveStudentWithPassport();
		// this.courseRepository.addHardcodedReviewsForCourse();
		// List<Review> reviews = new ArrayList<>();
		// reviews.add(new Review("4", "Description4"));
		// reviews.add(new Review("5", "Description5"));
		// this.courseRepository.addReviewsForCourse(10003L, reviews);

		// this.studentRepository.insertHardCodedStudentAndCourse();
		// this.studentRepository.insertStudentAndCourse(new Student("TestStudent5"), new Course("TestCourse4"));

		// this.employeeRepository.insert(new PartTimeEmployee("PartTimeEmployee1", new BigDecimal("50")));
		// this.employeeRepository.insert(new FullTimeEmployee("FullTimeEmployee1", new BigDecimal("10000")));

		// this.logger.info("All Employees: {}", this.employeeRepository.retrieveAllEmployees());
		// this.logger.info("All FullTimeEmployees: {}", this.employeeRepository.retrieveAllFullTimeEmployees());
		// this.logger.info("All PartTimeEmployees: {}", this.employeeRepository.retrieveAllPartTimeEmployees());

	}
}
