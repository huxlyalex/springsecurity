package com.example.springsecurity.repository;

import com.example.springsecurity.entity.Student;
import com.example.springsecurity.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer>
{
   @Query("SELECT e FROM Users e WHERE e.firstName = :firstName")
   Users findByFirstName(@Param("firstName") String firstName);
}
