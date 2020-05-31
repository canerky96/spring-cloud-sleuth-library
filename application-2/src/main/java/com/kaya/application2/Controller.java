package com.kaya.application2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping
public class Controller {

  @Autowired RestTemplate restTemplate;

  @GetMapping
  public String getHelloFromApplication1() {
    return restTemplate.getForObject("http://localhost:5050", String.class);
  }
}
