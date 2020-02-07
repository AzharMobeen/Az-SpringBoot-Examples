package com.az.multithreading.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServerDetailService {

	@Value("${HOSTNAME : LOCAL}")
	private String hostName;
	
	public String getServerDetail() {
		System.out.println(hostName.substring(hostName.length()-5));
		return hostName.substring(hostName.length()-5);
	}
}
