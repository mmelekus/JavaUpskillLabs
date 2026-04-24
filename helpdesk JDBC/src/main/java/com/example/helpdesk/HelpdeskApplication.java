package com.example.helpdesk;

import com.example.helpdesk.repository.DepartmentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelpdeskApplication {

//	@Autowired
//	private DepartmentRepository departmentRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

//	@PostConstruct
//	public void inspect() {
//		System.out.println("=== Loading department with ID 1 ===");
//		departmentRepository.findById(1L).ifPresent(dept -> {
//			System.out.println("Department: " + dept.getName());
//			System.out.println("Ticket count: " + dept.getTickets().size());
//			dept.getTickets().forEach(t ->
//					System.out.println("  Ticket: " + t.getTitle() + " [" + t.getStatus() + "]")
//			);
//		});
//	}
}