package com.tpe.dto;

import com.tpe.domain.Student;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {


    private Long id; // normalde DTO olmaz

    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name cannot be white space")
    @Size(min = 2, max = 25, message = "first name '${validatedValue}' must be between {min} and {max} long")
    private String firstName;

    private String lastName;

    private Integer grade;

    @Email(message = "provide valid email")
    private String email;

    private String phoneNumber;

    private LocalDateTime createDate = LocalDateTime.now();


    public StudentDTO(Student student) {
        this.id = student.getId();
        this.firstName = student.getName(); // firstName: farkli isim yapinca nasil oldugunu service'te gorecegiz
        this.lastName = student.getLastName();
        this.grade = student.getGrade();
        this.email = student.getEmail();
        this.phoneNumber = student.getPhoneNumber();
        this.createDate = student.getCreateDate();
    }
}
