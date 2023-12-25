package com.web.bookservice;

import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(BookserviceApplication.class, args);
	}
	
}
