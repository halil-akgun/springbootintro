package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter // getter yazmaya gerek kalmadi  (hem class seviyede hem field seviyede olabilir)
@Setter // setter yazmaya gerek kalmadi
@AllArgsConstructor // parametreli constructor yazmaya gerek kalmadi
//@RequiredArgsConstructor // sadece final olan fieldlar ile constr. olusur, @NoArgsConstructor kullanilamaz
@NoArgsConstructor // parametresiz constructor yazmaya gerek kalmadi
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // setId olmayacak
    private Long id;

    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name cannot be white space")
    @Size(min = 2, max = 25, message = "first name '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 25)
    private String name;
//    private final String name; // @RequiredArgsConstructor icin ornek

    @Column(nullable = false, length = 25)
    private /*final*/ String lastName;

    private /*final*/ Integer grade;

    @Column(nullable = false, unique = true)
    @Email(message = "provide valid email")
    private /*final*/ String email;

    private /*final*/ String phoneNumber;

    @Setter(AccessLevel.NONE) // setId olmayacak
    private LocalDateTime createDate = LocalDateTime.now();

    @OneToMany(mappedBy = "student")
    private List<Book> bookList = new ArrayList<>();
}
