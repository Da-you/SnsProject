package com.prj.sns_today;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SnsTodayApplication {

  static {
    System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
  } // host is down 처리용

  public static void main(String[] args) {
    SpringApplication.run(SnsTodayApplication.class, args);
  }

}
