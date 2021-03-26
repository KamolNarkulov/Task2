package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Input;
import uz.pdp.task2.entity.Problem;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.InputDto;
import uz.pdp.task2.repository.InputRepository;
import uz.pdp.task2.repository.ProblemRepository;
import uz.pdp.task2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    UserRepository userRepository;

    // CREATE
    public ApiResponse add(InputDto inputDto){
        Optional<User> optionalUser = userRepository.findById(inputDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found", false);

        Optional<Problem> optionalProblem = problemRepository.findById(inputDto.getProblemId());
        if (!optionalProblem.isPresent())
            return new ApiResponse("Problem not found", false);


        Input input = new Input();
        input.setCode(inputDto.getCode());
        input.setScore(inputDto.getScore());
        input.setProblem(optionalProblem.get());
        input.setUser(optionalUser.get());
        inputRepository.save(input);
        return new ApiResponse("Saved", true);
    }

    // READ
    public List<Input> get(){
        return inputRepository.findAll();
    }

    // READ BY ID
    public Input getById(Integer id){
        Optional<Input> optionalInput = inputRepository.findById(id);
        return optionalInput.orElse(null);
    }

    // UPDATE
    public ApiResponse edit(Integer id, InputDto inputDto){
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent())
            return new ApiResponse("Input not found", false);

        Optional<Problem> optionalProblem = problemRepository.findById(inputDto.getProblemId());
        if (!optionalProblem.isPresent())
            return new ApiResponse("Problem not found", false);

        Optional<User> optionalUser = userRepository.findById(inputDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found", false);


        Input input = optionalInput.get();
        input.setCode(inputDto.getCode());
        input.setScore(inputDto.getScore());
        input.setProblem(optionalProblem.get());
        input.setUser(optionalUser.get());
        inputRepository.save(input);
        return new ApiResponse("Edited", true);
    }

    // DELETE
    public ApiResponse delete(Integer id){
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent())
            return new ApiResponse("Input not found", false);

        inputRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
