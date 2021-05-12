package com.jx.reddit;

import com.jx.reddit.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class RedditCloneBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditCloneBackendApplication.class, args);
	}

}
