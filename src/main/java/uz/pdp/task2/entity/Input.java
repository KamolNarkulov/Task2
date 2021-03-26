package uz.pdp.task2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Input {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String code;

    @Column(unique = true, nullable = false)
    private Integer score;

    @ManyToOne
    private User user;

    @OneToOne
    private Problem problem;
}
