package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
