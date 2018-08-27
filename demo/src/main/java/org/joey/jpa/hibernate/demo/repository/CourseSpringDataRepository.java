package org.joey.jpa.hibernate.demo.repository;

import java.util.List;

import org.joey.jpa.hibernate.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "courses")
public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
	List<Course> findByName(String name);

	List<Course> findByNameAndId(String name, long id);

	List<Course> findByNameOrderByIdDesc(String name);

	List<Course> deleteByName(String name);

	@Query("select c from Course c where name like 'Course2'")
	List<Course> coursesWithCourse2InName(String name);

	@Query(value = "select * from Course c where name like 'Course2'", nativeQuery = true)
	List<Course> coursesWithCourse2InNameUsingNativeQuery(String name);

	@Query(name = "query_get_all_with3_courses")
	List<Course> coursesWith3InNameUsingNamedQuery(String name);
}
