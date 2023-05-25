package com.demo;

import com.demo.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
public class SpringSecurityLatestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityLatestApplication.class, args);
	}

	@Autowired
	private EmailSenderService emailSenderService;
	@EventListener(ApplicationReadyEvent.class)
	@Scheduled(cron = "0 */5 * * * *")
	public void triggerMail(){
		emailSenderService.sendEmail("venkatmakka1432@gmail.com",
				"This is From Spring Restaurant_Food_Items Application Invitation --- Venkat..",
				"conformation mail");
	}
}
