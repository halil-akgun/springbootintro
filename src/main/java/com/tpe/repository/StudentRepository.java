package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // opsiyonel (extends yaptigimiz icin)
public interface StudentRepository extends JpaRepository<Student, Long> {


    boolean existsByEmail(String email);

    List<Student> findByLastName(String lastName);

    // JPQL - FPA'nin bilmedigi ve findByLastName gibi turetilmemis olan bir methot, query ile ogretiyoruz
    @Query("SELECT s FROM Student s WHERE s.grade=:pGrade")
    // @Param("pGrade") ile grade aliniyor - s: alias
    List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade); // findByLastGrade de olabilir aslinda(query'ye gerek kalmaz)

    //SQL
    @Query(value = "SELECT * FROM student s WHERE s.grade=:pGrade", nativeQuery = true)//nativeQuery:HQL/JPQL degil
    List<Student> findAllEqualsGradeWithSQL(@Param("pGrade") Integer grade);

    // JPQL - StudentDTO(s) = constructor - s: gelen record
    @Query("SELECT new com.tpe.dto.StudentDTO(s) FROM Student s WHERE s.id=:id")
    Optional<StudentDTO> findStudentDTOById(@Param("id") Long id);

}
