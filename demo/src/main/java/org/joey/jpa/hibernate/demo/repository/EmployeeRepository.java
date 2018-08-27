package org.joey.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.joey.jpa.hibernate.demo.entity.Employee;
import org.joey.jpa.hibernate.demo.entity.FullTimeEmployee;
import org.joey.jpa.hibernate.demo.entity.PartTimeEmployee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EmployeeRepository {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	public void insert(Employee employee) {
		this.em.persist(employee);
	}

	public List<PartTimeEmployee> retrieveAllPartTimeEmployees() {
		// return this.em.createQuery("select e from Employee e", Employee.class).getResultList();
		return this.em.createQuery("select e from PartTimeEmployee e", PartTimeEmployee.class).getResultList();
	}

	public List<FullTimeEmployee> retrieveAllFullTimeEmployees() {
		// return this.em.createQuery("select e from Employee e", Employee.class).getResultList();
		return this.em.createQuery("select e from FullTimeEmployee e", FullTimeEmployee.class).getResultList();
	}
}
