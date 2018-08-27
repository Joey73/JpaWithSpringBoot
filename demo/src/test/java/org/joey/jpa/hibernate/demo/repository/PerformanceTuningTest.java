package org.joey.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.joey.jpa.hibernate.demo.DemoApplication;
import org.joey.jpa.hibernate.demo.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class PerformanceTuningTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository repository;

	@Autowired
	EntityManager em;

	@Test
	@Transactional
	public void creatingNPlusOneProblem() {
		List<Course> courses = this.em //
				.createNamedQuery("query_get_all_courses", Course.class) //
				.getResultList();

		for (Course course : courses) {
			this.logger.info("Course: {} Students: {}", course, course.getStudents());
		}
	}

	@Test
	@Transactional
	public void solvingNPlusOneProblem_EntityGraph() {
		EntityGraph<Course> entityGraph = this.em.createEntityGraph(Course.class);
		entityGraph.addSubgraph("students");

		List<Course> courses = this.em //
				.createNamedQuery("query_get_all_courses", Course.class) //
				.setHint("javax.persistence.loadgraph", entityGraph) //
				.getResultList();

		for (Course course : courses) {
			this.logger.info("Course: {} Students: {}", course, course.getStudents());
		}
	}

	@Test
	@Transactional
	public void solvingNPlusOneProblem_JoinFetch() {
		EntityGraph<Course> entityGraph = this.em.createEntityGraph(Course.class);
		entityGraph.addSubgraph("students");

		List<Course> courses = this.em //
				.createNamedQuery("query_get_all_courses_join_fetch", Course.class) //
				.getResultList();

		for (Course course : courses) {
			this.logger.info("Course: {} Students: {}", course, course.getStudents());
		}
	}
}
