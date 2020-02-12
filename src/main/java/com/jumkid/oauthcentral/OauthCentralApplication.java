package com.jumkid.oauthcentral;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@SpringBootApplication
public class OauthCentralApplication extends SpringBootServletInitializer {

	@Value("${server.port}")
	private String port;

	public static void main(String[] args) {
		SpringApplication.run(OauthCentralApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@EventListener({ApplicationReadyEvent.class})
	public void applicationReadyEvent() {
		System.out.println("OAuth Central App started ... launching browser now");
		try {
			browse(new URI(String.format("http://localhost:%s/", port)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void browse(URI uri) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				log.warn("Failed to launch web browser 1 {}", e.getMessage());
			}
		} else {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("rundll32 url.dll,FileProtocolHandler " + uri);
			} catch (IOException e) {
				log.warn("Failed to launch web browser 2 {}", e.getMessage());
			}
		}
	}

}
