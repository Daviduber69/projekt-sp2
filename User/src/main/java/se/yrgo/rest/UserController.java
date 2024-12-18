package se.yrgo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.data.UserRepository;
import se.yrgo.domain.UserEntity;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {this.userRepository = userRepository;}

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public UserList getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return new UserList(users);
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createUser = userRepository.save(user);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
