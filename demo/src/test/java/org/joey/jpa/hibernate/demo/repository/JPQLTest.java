package org.joey.jpa.hibernate.demo.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.joey.jpa.hibernate.demo.DemoApplication;
import org.joey.jpa.hibernate.demo.entity.Course;
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
public class JPQLTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	public void findAllCourses_raw() {
		Query query = this.em.createQuery("select c from Course c");
		List resultList = query.getResultList();
		this.logger.info("select c from Course c - Result: {}", resultList);
	}

	@Test
	public void findAllCourses_raw_namedQuery() {
		Query query = this.em.createNamedQuery("query_get_all_courses");
		List resultList = query.getResultList();
		this.logger.info("select c from Course c - Result: {}", resultList);
	}

	@Test
	public void findAllCourses_typed() {
		TypedQuery<Course> query = this.em.createQuery("select c from Course c", Course.class);
		List<Course> resultList = query.getResultList();
		this.logger.info("select c from Course c - Result: {}", resultList);
	}

	@Test
	public void findAllCourses_typed_namedQuery() {
		TypedQuery<Course> query = this.em.createNamedQuery("query_get_all_courses", Course.class);
		List<Course> resultList = query.getResultList();
		this.logger.info("select c from Course c - Result: {}", resultList);
	}

	@Test
	public void findCourse_where() {
		TypedQuery<Course> query = this.em.createQuery("select c from Course c where name like '%3'", Course.class);
		List<Course> resultList = query.getResultList();
		this.logger.info("select c from Course c where name like '%3' - Result: {}", resultList);
	}

	@Test
	public void findCourse_where_namedQuery() {
		TypedQuery<Course> query = this.em.createNamedQuery("query_get_all_with3_courses", Course.class);
		List<Course> resultList = query.getResultList();
		this.logger.info("select c from Course c where name like '%3' - Result: {}", resultList);
		assertEquals(1, resultList.size());
	}

	@Test
	public void jpql_courses_without_student() {
		TypedQuery<Course> query = this.em.createQuery("Select c from Course c where c.students is empty", Course.class);
		List<Course> resultList = query.getResultList();
		this.logger.info("Results: {}", resultList);
		assertEquals(1, resultList.size());
	}

	@Test
	public void jpql_courses_with_atleast_2_students() {
		TypedQuery<Course> query = this.em.createQuery("Select c from Course c where size(c.students) >= 2", Course.class);
		List<Course> resultList = query.getResultList();
		this.logger.info("Results: {}", resultList);
		assertEquals(1, resultList.size());
	}

	@Test
	public void jpql_courses_ordered_by_students() {
		TypedQuery<Course> query = this.em.createQuery("Select c from Course c order by size(c.students)", Course.class);
		List<Course> resultList = query.getResultList();
		this.logger.info("Results: {}", resultList);
	}

	@Test
	public void jpql_students_with_passports_in_a_certain_pattern() {
		TypedQuery<Student> query = this.em.createQuery("Select s from Student s where s.passport.number like '%22%'", Student.class);
		List<Student> resultList = query.getResultList();
		this.logger.info("Results: {}", resultList);
		assertEquals(1, resultList.size());
	}

	@Test
	public void join() {
		Query query = this.em.createQuery("Select c, s from Course c JOIN c.students s");
		List<Object[]> resultList = query.getResultList();
		this.logger.info("Results Size: {}", resultList.size());
		for (Object[] result : resultList) {
			this.logger.info("Course {}, Student {}", result[0], result[1]);
		}
	}

	@Test
	public void left_join() {
		Query query = this.em.createQuery("Select c, s from Course c LEFT JOIN c.students s");
		List<Object[]> resultList = query.getResultList();
		this.logger.info("Results Size: {}", resultList.size());
		for (Object[] result : resultList) {
			this.logger.info("Course {}, Student {}", result[0], result[1]);
		}
	}

	@Test
	public void cross_join() {
		Query query = this.em.createQuery("Select c, s from Course c, Student s");
		List<Object[]> resultList = query.getResultList();
		this.logger.info("Results Size: {}", resultList.size());
		for (Object[] result : resultList) {
			this.logger.info("Course {}, Student {}", result[0], result[1]);
		}
	}
}
