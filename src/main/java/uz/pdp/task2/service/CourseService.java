package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Course;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.CourseDto;
import uz.pdp.task2.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    // CREATE
    public ApiResponse add(CourseDto courseDto){
        boolean existsByName = courseRepository.existsByName(courseDto.getName());
        if (existsByName)
            return new ApiResponse("Already exists", false);

        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        courseRepository.save(course);
        return new ApiResponse("Saved", true);
    }

    // READ
    public List<Course> get(){
        return courseRepository.findAll();
    }

    // READ BY ID
    public Course getById(Integer id){
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.orElse(null);
    }

    // UPDATE
    public ApiResponse edit(Integer id, CourseDto courseDto){
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent())
            return new ApiResponse("Not Found", false);

        boolean existsByNameAndIdNot = courseRepository.existsByNameAndIdNot(courseDto.getName(), id);
        if (existsByNameAndIdNot)
            return new ApiResponse("Already exists", false);

        Course course = optionalCourse.get();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        courseRepository.save(course);
        return new ApiResponse("Edited", true);
    }

    // DELETE
    public ApiResponse delete(Integer id){
        try {
            courseRepository.deleteById(id);
            return new ApiResponse("Deleted", true);
        }catch (Exception e){
            return new ApiResponse("Not Found", false);
        }
    }
}
