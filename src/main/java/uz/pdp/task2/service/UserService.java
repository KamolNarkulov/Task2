package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Course;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.CourseDto;
import uz.pdp.task2.payload.UserDto;
import uz.pdp.task2.repository.CourseRepository;
import uz.pdp.task2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // CREATE
    public ApiResponse add(UserDto userDto){
        boolean existsByEmail = userRepository.existsByEmail(userDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Already exists", false);

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("Saved", true);
    }

    // READ
    public List<User> get(){
        return userRepository.findAll();
    }

    // READ BY ID
    public User getById(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    // UPDATE
    public ApiResponse edit(Integer id, UserDto userDto){
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("Not Found", false);

        boolean existsByEmailAndIdNot = userRepository.existsByEmailAndIdNot(userDto.getEmail(), id);
        if (existsByEmailAndIdNot)
            return new ApiResponse("Already exists", false);

        User user = optionalUser.get();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("Edited", true);
    }

    // DELETE
    public ApiResponse delete(Integer id){
        try {
            userRepository.deleteById(id);
            return new ApiResponse("Deleted", true);
        }catch (Exception e){
            return new ApiResponse("Not Found", false);
        }
    }
}
