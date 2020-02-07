package com.az.multithreading.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.az.multithreading.entities.User;
import com.az.multithreading.services.ServerDetailService;
import com.az.multithreading.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServerDetailService serverDetailService;    
    
    @GetMapping("/")
    public String root() {
    	return "{healthy : true}";
    }
    
    @GetMapping("/hello")
    public String hello() {
    	return "Hello World! V-1 "+serverDetailService.getServerDetail();
    }
    
    @GetMapping("/getUsers")
    public List<User> getUserList(){
        log.info("UserController getUserList start...");
        List<User> userList = userService.getAllUsers();
        if(CollectionUtils.isEmpty(userList))
            log.info("Size of user List :::: {}",userList.size());
        log.info("UserController getUserList end...");

        return userList;
    }
    
    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces ="application/json")
    public ResponseEntity saveUser(@RequestParam(value = "file") MultipartFile[] files) throws Exception {
    	
    	for (MultipartFile multipartFile : files) {
			userService.saveUser(multipartFile);
		}
    	
    	return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users")
    public CompletableFuture<ResponseEntity> findAllUsers(){
    	return userService.getAllUsersByAsync().thenApply(ResponseEntity::ok);
    }
    
    @GetMapping("/usersAsync")
    public CompletableFuture<ResponseEntity>  findAllUsersByMultipleThreads(){
    	CompletableFuture<List<User>> future1 = userService.getAllUsersByAsync();
    	CompletableFuture<List<User>> future2 = userService.getAllUsersByAsync();
    	CompletableFuture<List<User>> future3 = userService.getAllUsersByAsync();    	
    	CompletableFuture.allOf(future1,future2,future3).join();
    	List<User> userList = new ArrayList<>();
		try {
			userList = future1.get();
			userList.addAll(future2.get());
	    	userList.addAll(future3.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}    	    
    	
    	return CompletableFuture.completedFuture(userList).thenApply(ResponseEntity::ok);
    } 
    
}
