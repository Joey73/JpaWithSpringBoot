package org.joey.jpa.hibernate.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import org.joey.jpa.hibernate.demo.DemoApplication;
import org.joey.jpa.hibernate.demo.entity.Course;
import org.joey.jpa.hibernate.demo.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class CourseRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository repository;

	@Autowired
	EntityManager em;

	@Test
	public void findById_basic() {
		this.logger.info("Test is running");
		Course course = this.repository.findById(10001L);
		assertEquals("TestCourse1", course.getName());
	}

	@Test
	@Transactional
	public void findById_firstLevelCacheDemo() {
		Course course = this.repository.findById(10001L);
		this.logger.info("First course retrieved: {}", course);
		Course course1 = this.repository.findById(10001L);
		this.logger.info("First course retrieved again: {}", course1);
		assertEquals("TestCourse1", course.getName());
		assertEquals("TestCourse1", course1.getName());
	}

	@Test
	@DirtiesContext
	public void deleteById_basic() {
		this.repository.deleteById(10002L);
		assertNull(this.repository.findById(10002L));
	}

	@Test
	@DirtiesContext
	public void save_basic() {
		// Get a course
		Course course = this.repository.findById(10001L);
		assertEquals("TestCourse1", course.getName());
		// Update
		course.setName("TestCourse1 updated");
		this.repository.save(course);
		// Get Course
		Course course1 = this.repository.findById(10001L);
		// Check
		assertEquals("TestCourse1 updated", course1.getName());
	}

	@Test
	@DirtiesContext
	public void playWithEntityManager() {
		this.repository.playWithEntityManger();
	}

	@Test
	@Transactional
	public void retrieveReviewsForCourse() {
		Course course = this.repository.findById(10001L);
		// Without @Transactional the following line would throw an error
		this.logger.info("course.getReviews(): {}", course.getReviews());
	}

	@Test
	@Transactional
	public void retrieveCourseForReview() {
		Review review = this.em.find(Review.class, 50001L);
		// Without @Transactional the following line would throw an error
		this.logger.info("review.getCourse(): {}", review.getCourse());
	}
}
