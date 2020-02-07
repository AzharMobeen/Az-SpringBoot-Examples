package com.az.multithreading.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.az.multithreading.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
