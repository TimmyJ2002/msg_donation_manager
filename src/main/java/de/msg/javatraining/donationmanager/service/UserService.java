package de.msg.javatraining.donationmanager.service;

import de.msg.javatraining.donationmanager.persistence.model.User;
import de.msg.javatraining.donationmanager.persistence.model.UserDTO;
import de.msg.javatraining.donationmanager.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
      //  return userList.stream().map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail())).collect(Collectors.toList());
        List<UserDTO> userDTOList = new ArrayList<>();
         for(int i=0; i<userList.size(); i++){
            userDTOList.add(new UserDTO(userList.get(i).getId(),userList.get(i).getUsername(),userList.get(i).getEmail()));
        }
         return userDTOList;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    public User saveUser(String username, String email, String password) throws InstanceAlreadyExistsException {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)){
            throw new InstanceAlreadyExistsException("Username or email already in use");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User savedUser = new User(username, email, encodedPassword);
        return userRepository.save(savedUser);
    }

    public UserDTO updateUser(Long userId, UserDTO updatedUserDTO) throws ChangeSetPersister.NotFoundException {
        // Retrieve the existing user from the database
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Update the fields that can be modified
        existingUser.setUsername(updatedUserDTO.getUsername());
        existingUser.setEmail(updatedUserDTO.getEmail());

        // Save the updated user
        User updatedUser = userRepository.save(existingUser);

        // Convert and return the updated user as DTO
        return convertToDTO(updatedUser);

    }
    public UserDTO findUserById(Long userId) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return convertToDTO(user);
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
