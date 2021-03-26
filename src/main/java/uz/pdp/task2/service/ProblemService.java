package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Problem;
import uz.pdp.task2.entity.Section;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.ProblemDto;
import uz.pdp.task2.repository.ProblemRepository;
import uz.pdp.task2.repository.SectionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    SectionRepository sectionRepository;

    // CREATE
    public ApiResponse add(ProblemDto problemDto){
        Optional<Section> optionalSection = sectionRepository.findById(problemDto.getSectionId());
        if (!optionalSection.isPresent())
            return new ApiResponse("Section not found", false);

        if (problemRepository.existsByTitleAndBody(problemDto.getTitle(), problemDto.getBody()))
            return new ApiResponse("Already exists", false);

        Problem problem = new Problem();
        problem.setTitle(problemDto.getTitle());
        problem.setBody(problemDto.getBody());
        problem.setSolution(problemDto.getSolution());
        problem.setSection(optionalSection.get());

        problemRepository.save(problem);
        return new ApiResponse("Saved", true);
    }

    // READ
    public List<Problem> get(){
        return problemRepository.findAll();
    }

    // READ BY ID
    public Problem getById(Integer id){
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        return optionalProblem.orElse(null);
    }

    // UPDATE
    public ApiResponse edit(Integer id, ProblemDto problemDto){
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        if (!optionalProblem.isPresent())
            return new ApiResponse("Problem not found", false);

        Optional<Section> optionalSection = sectionRepository.findById(problemDto.getSectionId());
        if (!optionalSection.isPresent())
            return new ApiResponse("Section not found", false);

        if (problemRepository.existsByTitleAndBodyAndIdNot(problemDto.getTitle(), problemDto.getBody(), id))
            return new ApiResponse("Already exists", false);

        Problem problem = optionalProblem.get();
        problem.setTitle(problemDto.getTitle());
        problem.setBody(problemDto.getBody());
        problem.setSolution(problemDto.getSolution());
        problem.setSection(optionalSection.get());
        problemRepository.save(problem);
        return new ApiResponse("Edited", true);
    }

    // DELETE
    public ApiResponse delete(Integer id){
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        if (!optionalProblem.isPresent())
            return new ApiResponse("Problem not found", false);

        problemRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
