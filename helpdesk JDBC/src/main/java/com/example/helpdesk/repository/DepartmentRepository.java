package com.example.helpdesk.repository;

import com.example.helpdesk.model.Department;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    // TODO 3: Add a derived query method that finds a Department by its name field.
    // The method signature is: Optional<Department> findByName(String name);
    // Spring Data JDBC supports the same method name derivation grammar as Spring Data JPA.
    // The generated query will be SQL, not JPQL: WHERE name = :name
    Optional<Department> findByName(String name);

    // TODO 4: Add a native SQL query that returns all departments ordered by name.
    // In Spring Data JDBC, @Query always means native SQL. There is no JPQL.
    // Use: @Query("SELECT * FROM departments ORDER BY name ASC")
    //
    // The correct import for @Query is:
    //   org.springframework.data.jdbc.repository.query.Query
    //
    // NOT org.springframework.data.jpa.repository.Query (the JPA version).
    // IntelliJ may offer both as autocomplete options. Select the jdbc one.
    // The JPA version compiles without error but is silently ignored at runtime.
    @Query("SELECT * FROM departments ORDER BY name ASC")
    List<Department> findAllOrderedByName();
}