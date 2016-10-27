package xxd.coc.battle.web.controller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * This class is for launching the web service using gradle bootRun as a standalone application  
 * also can built a war file to deploy to web container like tomcat
 * 
 * Note: This file must be put in the same package as the controller java files
 * @author xualu
 *
 */

@SpringBootApplication
public class App extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
	
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
