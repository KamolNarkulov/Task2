package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    boolean existsByTitleAndBody(String title, String body);

    boolean existsByTitleAndBodyAndIdNot(String title, String body, Integer id);
}
