package de.msg.javatraining.donationmanager.controller.user;

import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.model.UserDTO;
import de.msg.javatraining.donationmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserDTO>findAll(){
        return userService.findAll();
    }

    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
        return new ResponseEntity<>("User s-a sters cu ok", HttpStatus.OK);
    }

    @PostMapping("/users/save")
    public ResponseEntity<User> createUser(@RequestBody User user) throws InstanceAlreadyExistsException {

        User newUser = userService.saveUser(user.getUsername(),user.getEmail(),user.getPassword());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("users/update/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO updatedUserDTO) throws ChangeSetPersister.NotFoundException {
        UserDTO updatedUser = userService.updateUser(userId, updatedUserDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("users/find_by_id/{userId}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long userId) throws ChangeSetPersister.NotFoundException {
        UserDTO user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
