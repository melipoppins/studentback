package com.crudstudentback.studentback.controller;

import com.crudstudentback.studentback.exception.ResourceNotFoundException;
import com.crudstudentback.studentback.model.Student;
import com.crudstudentback.studentback.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin( origins = "http://localhost:4200" )
@RequestMapping( "/api/v1" )
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping( "/students" )
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping( "/students/{id}" )
    public ResponseEntity<Student> getStudentById( @PathVariable(value="id") Long studentId ) throws ResourceNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow( () -> new ResourceNotFoundException( "Student not found for this id:: " + studentId ));
        return ResponseEntity.ok().body( student );
    }

    @PostMapping( "/students" )
    public Student createStudent( @Valid @RequestBody Student student ) {
        return studentRepository.save(student);
    }

    @PutMapping( "/students/{id}" )
    public ResponseEntity<Student> updateStudent( @PathVariable( value = "id") Long studentId,
                                                  @Valid @RequestBody Student studentDetails ) throws ResourceNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow( () -> new ResourceNotFoundException( "Student not found for this id:: " + studentId ));

        student.setName(studentDetails.getName());
        student.setGender(studentDetails.getGender());
        student.setPhone(studentDetails.getPhone());
        student.setAddress(studentDetails.getAddress());
        final Student updateStudent = studentRepository.save(student);
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping( "/students/{id}" )
    public Map<String, Boolean> deleteStudent(@PathVariable( value = "id") Long studentId) throws ResourceNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow( () -> new ResourceNotFoundException( "Student not found for this id:: " + studentId ));

        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
