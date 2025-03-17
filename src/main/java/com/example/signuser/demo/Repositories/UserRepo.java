package com.example.signuser.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.signuser.demo.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Integer> {

}
