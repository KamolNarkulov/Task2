package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Course;
import uz.pdp.task2.entity.Section;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.SectionDto;
import uz.pdp.task2.repository.CourseRepository;
import uz.pdp.task2.repository.SectionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    CourseRepository courseRepository;

    // CREATE
    public ApiResponse add(SectionDto sectionDto){
        Optional<Course> optionalCourse = courseRepository.findById(sectionDto.getCourseId());
        if (!optionalCourse.isPresent())
            return new ApiResponse("Course not found", false);
        Course course = optionalCourse.get();

        if (sectionRepository.existsByName(sectionDto.getName()))
            return new ApiResponse("Already exists", false);

        Section section = new Section();
        section.setName(sectionDto.getName());
        section.setDescription(sectionDto.getDescription());
        section.setCourse(course);
        sectionRepository.save(section);
        return new ApiResponse("Saved", true);
    }

    // READ
    public List<Section> get(){
        return sectionRepository.findAll();
    }

    // READ BY ID
    public Section getById(Integer id){
        Optional<Section> optionalSection = sectionRepository.findById(id);
        return optionalSection.orElse(null);
    }

    // UPDATE
    public ApiResponse edit(Integer id, SectionDto sectionDto){
        Optional<Section> optionalSection = sectionRepository.findById(id);
        if (!optionalSection.isPresent())
            return new ApiResponse("Section not found", false);

        Optional<Course> optionalCourse = courseRepository.findById(sectionDto.getCourseId());
        if (!optionalCourse.isPresent())
            return new ApiResponse("Course not found", false);

        if (sectionRepository.existsByNameAndIdNot(sectionDto.getName(), id))
            return new ApiResponse("Already exists", false);

        Section section = optionalSection.get();
        section.setName(sectionDto.getName());
        section.setDescription(sectionDto.getDescription());
        section.setCourse(optionalCourse.get());
        sectionRepository.save(section);
        return new ApiResponse("Edited", true);
    }

    // DELETE
    public ApiResponse delete(Integer id){
        Optional<Section> optionalSection = sectionRepository.findById(id);
        if (!optionalSection.isPresent())
            return new ApiResponse("Section not found", false);

        sectionRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
