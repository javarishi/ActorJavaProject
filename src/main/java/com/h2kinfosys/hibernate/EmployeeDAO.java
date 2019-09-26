package com.h2kinfosys.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class EmployeeDAO {
	
	SessionFactory sessionFactory = null;
	
	protected void setUp() throws Exception {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			System.out.println(sessionFactory);
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}

	
	
	public EmployeeDAO() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void saveEmployee(Employee emp) {
		Session session = null;
		Transaction transaction = null;
		System.out.println("Employee save : start");
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if(emp != null) {
				session.save(emp);
			}
			transaction.commit();
			session.close();
			System.out.println("Employee save : End");
		}catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}
	
	
	public void getAllEmployees() {
		Session session = null;
		Transaction transaction = null;
		System.out.println("Employee getAllEmployees : start");
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			List<Employee> employees = session.createQuery("from Employee").list();
			for(Employee eachEmp : employees) {
				System.out.println(eachEmp.getId() +  "  " + eachEmp.getFirstName() + "  " + eachEmp.getLastName());
			}
			transaction.commit();
			session.close();
			System.out.println("Employee getAllEmployees : End");
		}catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	
	public static void main(String[] args) {
		EmployeeDAO empDao = new EmployeeDAO();
		Employee emp = new Employee();
		emp.setFirstName("David");
		emp.setLastName("Nix");
		emp.setSalary(5000);
		//empDao.saveEmployee(emp);
		empDao.getAllEmployees();
		
	}
}
