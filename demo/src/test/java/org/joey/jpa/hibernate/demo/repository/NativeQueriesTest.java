package org.joey.jpa.hibernate.demo.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
public class NativeQueriesTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	public void native_queries_raw() {
		Query query = this.em.createNativeQuery("select * from course");
		List resultList = query.getResultList();
		this.logger.info("select * from course - Result: {}", resultList);
	}

	@Test
	public void native_queries_typed() {
		Query query = this.em.createNativeQuery("select * from course where is_deleted=0", Course.class);
		List resultList = query.getResultList();
		this.logger.info("select * from course - Result: {}", resultList);
	}

	@Test
	public void native_queries_typed_with_parameter() {
		Query query = this.em.createNativeQuery("select * from course where id = ?", Course.class);
		query.setParameter(1, 10001L);
		List resultList = query.getResultList();
		this.logger.info("select * from course where id = ? - Result: {}", resultList);
		assertEquals(1, resultList.size());
	}

	@Test
	public void native_queries_typed_with_named_parameter() {
		Query query = this.em.createNativeQuery("select * from course where id = :id", Course.class);
		query.setParameter("id", 10001L);
		List resultList = query.getResultList();
		this.logger.info("select * from course where id = :id - Result: {}", resultList);
		assertEquals(1, resultList.size());
	}
}
