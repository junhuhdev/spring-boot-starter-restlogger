package huh.enterprises.restlogger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RestTemplate restTemplate;

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", Object.class));
    }

    @PostMapping("/users")
    public ResponseEntity createUsers(@RequestBody User user) {
        return ResponseEntity.ok(restTemplate.postForObject("https://jsonplaceholder.typicode.com/posts", user, Object.class));
    }

    @PostMapping("/users/error")
    public ResponseEntity createUsersError(@RequestBody User user) {
        return ResponseEntity.ok(restTemplate.postForObject("https://jsonplaceholder.typicode.com/error", user, Object.class));
    }

    @PutMapping("/users")
    public ResponseEntity updateUsers(@RequestBody User user) {
        HttpEntity<User> requestUpdate = new HttpEntity<User>(user, new LinkedMultiValueMap());
        return ResponseEntity.ok(restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/1", HttpMethod.PUT, requestUpdate, User.class).getBody());
    }

}