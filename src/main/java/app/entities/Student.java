package app.entities;

import app.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@DynamicUpdate
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

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void created(){
        createdAt = LocalDateTime.now();
    }
}
