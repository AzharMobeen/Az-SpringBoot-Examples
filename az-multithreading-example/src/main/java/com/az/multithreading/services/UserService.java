package com.az.multithreading.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.az.multithreading.entities.User;
import com.az.multithreading.entities.User.UserBuilder;
import com.az.multithreading.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	
	
    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers(){
        //this.insertDumyData();
        return userRepository.findAll();
    }
   

    @Async
    public CompletableFuture<List<User>> saveUser(MultipartFile file) throws Exception{
    	long startTime = System.currentTimeMillis();
    	
    	List<User> userList = this.parseCSVFile(file);
    	
    	log.info("User list going to save Size {}",userList.size());
    	long endTime = System.currentTimeMillis();
    	userRepository.saveAll(userList);
    	
    	log.info("Total Time :: {}",(endTime-startTime));
    	return CompletableFuture.completedFuture(userList);
    }
    @Async
    public CompletableFuture<List<User>> getAllUsersByAsync(){
    	log.info("Get user list by "+Thread.currentThread().getName());
        return CompletableFuture.completedFuture(userRepository.findAll());
    }
    
    private List<User> parseCSVFile(final MultipartFile file) throws Exception{
    	final List<User> userList = new ArrayList<>();
    	try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
    		String line;
    		while((line=bufferedReader.readLine())!=null) {
    			final String[] data = line.split(",");
    			final User user = User.builder()
    								.name(data[0])
    								.email(data[1])
    								.gender(data[2])
    								.build();    			
    			userList.add(user);    			
    		}
    		
    	}catch (final IOException e) {
    		log.error("Faild to pass CSV file {}",e);    		
		}
    	
    	return userList;
    }
    
}
