package app.entities;

import app.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    private int age;
    private String phone;
    @Column(name = "course_id")
    private Long courseId;
    private String email;
    @Enumerated(EnumType.STRING)
    private StudentStatus status;
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
}
