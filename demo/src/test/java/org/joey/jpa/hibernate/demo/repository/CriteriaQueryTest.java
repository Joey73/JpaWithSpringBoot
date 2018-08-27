package org.joey.jpa.hibernate.demo.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joey.jpa.hibernate.demo.DemoApplication;
import org.joey.jpa.hibernate.demo.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class CriteriaQueryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	public void all_courses() {
		// "select c from Course c"

		// 1.) Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // Expected result object

		// 2.) Define roots for tables which are involved in the query.
		Root<Course> courseRoot = cq.from(Course.class); // from Course c

		// 3.) Define Predicates etc using Criteria Builder
		// Not needed in this example

		// 4.) Add Predicates etc. to the Criteria Query
		// Not needed in this example

		// 5.) Build the TypedQuery using the entity manager and criteria query.
		TypedQuery<Course> query = this.em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		this.logger.info("Typed Query: {}", resultList);

		assertEquals(3, resultList.size());
	}

	@Test
	public void all_courses_having_Course1() {
		// "select c from Course c where name like '%Course1'"

		// 1.) Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // Expected result object

		// 2.) Define roots for tables which are involved in the query.
		Root<Course> courseRoot = cq.from(Course.class); // from Course c

		// 3.) Define Predicates etc using Criteria Builder
		Predicate likeCourse1 = cb.like(courseRoot.get("name"), "%Course1");

		// 4.) Add Predicates etc. to the Criteria Query
		cq.where(likeCourse1);

		// 5.) Build the TypedQuery using the entity manager and criteria query.
		TypedQuery<Course> query = this.em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		this.logger.info("Typed Query: {}", resultList);

		assertEquals(1, resultList.size());
	}

	@Test
	public void all_courses_without_students() {
		// "select c from Course c where c.students is empty"

		// 1.) Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // Expected result object

		// 2.) Define roots for tables which are involved in the query.
		Root<Course> courseRoot = cq.from(Course.class); // from Course c

		// 3.) Define Predicates etc using Criteria Builder
		Predicate studentsIsEmpty = cb.isEmpty(courseRoot.get("students"));

		// 4.) Add Predicates etc. to the Criteria Query
		cq.where(studentsIsEmpty);

		// 5.) Build the TypedQuery using the entity manager and criteria query.
		TypedQuery<Course> query = this.em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		this.logger.info("Typed Query: {}", resultList);

		assertEquals(1, resultList.size());
	}

	@Test
	public void join() {
		// "select c from Course c join c.students s"

		// 1.) Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // Expected result object

		// 2.) Define roots for tables which are involved in the query.
		Root<Course> courseRoot = cq.from(Course.class); // from Course c

		// 3.) Define Predicates etc using Criteria Builder
		Join<Object, Object> join = courseRoot.join("students");

		// 4.) Add Predicates etc. to the Criteria Query

		// 5.) Build the TypedQuery using the entity manager and criteria query.
		TypedQuery<Course> query = this.em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		this.logger.info("Typed Query: {}", resultList);

		assertEquals(4, resultList.size());
	}

	@Test
	public void left_join() {
		// "select c from Course c left join c.students s"

		// 1.) Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // Expected result object

		// 2.) Define roots for tables which are involved in the query.
		Root<Course> courseRoot = cq.from(Course.class); // from Course c

		// 3.) Define Predicates etc using Criteria Builder
		Join<Object, Object> join = courseRoot.join("students", JoinType.LEFT);

		// 4.) Add Predicates etc. to the Criteria Query

		// 5.) Build the TypedQuery using the entity manager and criteria query.
		TypedQuery<Course> query = this.em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		this.logger.info("Typed Query: {}", resultList);

		assertEquals(5, resultList.size());
	}
}
