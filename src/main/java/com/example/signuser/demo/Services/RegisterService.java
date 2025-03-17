package com.example.signuser.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.signuser.demo.UserModel;
import com.example.signuser.demo.Connectors.Connector;
import com.example.signuser.demo.DemoApplication;
import com.example.signuser.demo.Repositories.UserRepo;

@Service
public class RegisterService {





@Autowired
private UserRepo userRepo;








public UserModel loadUser(UserModel userModel){

 List<UserModel> A = userRepo.findAll();   

 boolean existEmail = A.stream().anyMatch(a -> a.getEmail().equals(userModel.getEmail()));

 if(existEmail){
UserModel user = A.stream().filter(a -> a.getEmail().equals(userModel.getEmail())).findFirst().orElse(null);

if(user.getPassword().equals(userModel.getPassword())){
    return user;
}else return null;
 }
return null;
}


public List<UserModel> getAllData(){
    List<UserModel> A = userRepo.findAll();
     return A;
}





public UserModel save(UserModel userModel){
    if(userModel.getPassword().length() >8 && userModel.getPassword().equals(userModel.getCopassword())){
        List<UserModel> A = userRepo.findAll();
        if(A.stream().anyMatch(data -> data.getEmail().equals(userModel.getEmail()))){
            return null;
        } 
        return userRepo.save(userModel);
    }
    return null;
}


}
