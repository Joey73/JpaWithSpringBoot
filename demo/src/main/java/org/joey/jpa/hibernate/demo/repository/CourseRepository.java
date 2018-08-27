package org.joey.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.joey.jpa.hibernate.demo.entity.Course;
import org.joey.jpa.hibernate.demo.entity.Review;
import org.joey.jpa.hibernate.demo.entity.ReviewRating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CourseRepository {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	public Course findById(Long id) {
		return this.em.find(Course.class, id);
	}

	// Insert and update
	public Course save(Course course) {
		if (course.getId() == null) {
			this.em.persist(course);
		} else {
			this.em.merge(course);
		}
		return course;
	}

	public void deleteById(Long id) {
		Course course = this.findById(id);
		this.em.remove(course);
	}

	public void playWithEntityManger() {
		Course course4 = new Course("TestCourse4");
		this.em.persist(course4);

		Course course1 = this.findById(10001L);
		course1.setName("TestCourse1 - Updated");
	}

	public void addHardcodedReviewsForCourse() {
		// Get the course 10003
		Course course = this.findById(10003L);
		this.logger.info("course.getReviews(): {}", course.getReviews());

		// Add 2 reviews to it
		Review review1 = new Review(ReviewRating.FOUR, "Description4");
		Review review2 = new Review(ReviewRating.FIVE, "Description5");

		// Setting the relationship
		course.addReview(review1);
		review1.setCourse(course);
		course.addReview(review2);
		review2.setCourse(course);

		// Save it to the DB
		this.em.persist(review1);
		this.em.persist(review2);
	}

	public void addReviewsForCourse(Long courseId, List<Review> reviews) {
		// Get the course
		Course course = this.findById(courseId);
		this.logger.info("course.getReviews(): {}", course.getReviews());

		// Add reviews to it
		for (Review review : reviews) {
			// Setting the relationship
			course.addReview(review);
			review.setCourse(course);

			// Save it to the DB
			this.em.persist(review);
		}
	}
}
