package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private static Long idCounter = 1L;
    @Override
    public Student add(Student student) {
        students.put(idCounter++, student);
        return student;
    }

    @Override
    public Student remove(Long id) {
        if (students.containsKey(id)) {
            return students.remove(id);
        }
        throw new EntityNotFoundException("Студент с id = " + id + "не существует! ");
    }

    @Override
    public Student update(Student student) {
        if (students.containsKey(student.getId())) {
            return students.put(student.getId(),student);
        }
        throw new EntityNotFoundException("Студент с id = " + student.getId() + "не существует! ");

    }

    @Override
    public Student get(Long id) {
        if (students.containsKey(id)) {
            return students.get(id);
        }
        throw new EntityNotFoundException("Студент с id = " + id + "не существует! ");

    }

    @Override
    public Collection<Student> getAll() {
        return students.values();
    }

    @Override
    public Collection<Student> getByAge(Integer age) {
        if (age <= 10 || age > 100) {
            throw new IncorrectArgumentException("Требуеться указать корректный возраст студента");
        }

        return students.values().stream()
                .filter(s ->s.getAge().equals(age))
                .collect(Collectors.toList());
    }
}
