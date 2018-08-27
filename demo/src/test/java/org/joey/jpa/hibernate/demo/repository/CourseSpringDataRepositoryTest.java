package org.joey.jpa.hibernate.demo.repository;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.joey.jpa.hibernate.demo.DemoApplication;
import org.joey.jpa.hibernate.demo.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class CourseSpringDataRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseSpringDataRepository repository;

	@Test
	public void findById_CoursePresent() {
		Optional<Course> courseOptional = this.repository.findById(10001L);
		assertTrue(courseOptional.isPresent());
	}

	@Test
	public void insertAndUpdate() {
		Course course = new Course("TestCourse4");
		this.repository.save(course); // Insert
		course.setName("TestCourse4 - updated");
		this.repository.save(course); // Update
	}

	@Test
	public void findAll() {
		this.logger.info("Courses: {}", this.repository.findAll());
	}

	@Test
	public void count() {
		this.logger.info("Courses count: {}", this.repository.count());
	}

	@Test
	public void sort() {
		Sort sort = new Sort(Sort.Direction.DESC, "name"); // .and([additional Sorting Criteria])
		this.logger.info("Sortd Courses: {}", this.repository.findAll(sort));
	}

	@Test
	public void pagination() {
		PageRequest pageRequest = PageRequest.of(0, 3);
		Page<Course> firstPage = this.repository.findAll(pageRequest);
		this.logger.info("First Page: {}", firstPage.getContent());

		Pageable secondPageable = firstPage.nextPageable();
		Page<Course> secondPage = this.repository.findAll(secondPageable);
		this.logger.info("Second Page: {}", secondPage.getContent());
	}

	@Test
	public void findUsingName() {
		this.logger.info("FindByName: {}", this.repository.findByName("TestCourse2"));
	}
}
