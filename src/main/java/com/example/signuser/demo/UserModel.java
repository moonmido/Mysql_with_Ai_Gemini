package com.example.signuser.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name="Regesters")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id ;

@Column(name = "Full_Name")
private String fname;

private String email;
private String password;

@Transient
private String copassword;

}
