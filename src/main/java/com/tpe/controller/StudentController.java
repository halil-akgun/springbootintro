package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // ozellestirilmis controller, status kodlar(200,404..) da gonderilir
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAll() { // return students + status code
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students); // students + status code=200
    }

    @PostMapping // http://localhost:8080/students + POST + JSON   (gelen json'i @RequestBody ile maple)
    public ResponseEntity<Map<String, String>> createStudent(@Valid @RequestBody Student student) {
//                                        @Valid: valid. islemi burada olur, sorun varsa repoya kadar gidilmez
        studentService.createStudent(student);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Student is created successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    // GET A STUDENT BY ID VIA REQUESTPARAM
    @GetMapping("/query") // http://localhost:8080/students/query?id=1
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id) {
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student); //ayni: return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // GET A STUDENT BY ID VIA PATHVARIABLE
    @GetMapping("/{id}") // http://localhost:8080/students/1
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id) {
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    // DELETE A STUDENT BY ID VIA PATHVARIABLE
    @DeleteMapping("/{id}") // http://localhost:8080/students/1
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Student is deleted successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK); // ayni: return ResponseEntity.ok(map);
    }

    // UPDATE A STUDENT BY ID VIA PATHVARIABLE
    @PutMapping("{id}") // http://localhost:8080/students/1 --> endPoint + id + JSON
    public ResponseEntity<Map<String, String>> updateStudent(
            @PathVariable Long id, @RequestBody StudentDTO studentDTO) { //PathVariable:tek parametre ise id yazilmyblr
        studentService.updateStudent(id, studentDTO);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Student is updated successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // PAGEABLE
    @GetMapping("/page") // http://localhost:8080/students/page?page=1&size=2&sort=name&direction=ASC
    public ResponseEntity<Page<Student>> getAllWithPage(
            @RequestParam("page") int page, // kacinci sayfa gelsin?
            @RequestParam("size") int size, // sayfa basi kac urun
            @RequestParam("sort") String prop, // hangi field'a gore siralanacak (opsiyonel)
            @RequestParam("direction") Sort.Direction direction // siralama turu (opsiyonel)
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop)); // of ile new'leme oluyor
        Page<Student> studentPage = studentService.getAllWithPage(pageable);

        return ResponseEntity.ok(studentPage);
    }

    // GET BY LASTNAME
    @GetMapping("/querylastname")
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName) {
        List<Student> list = studentService.findStudent(lastName);
        return ResponseEntity.ok(list);
    }

}
