package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static Long idCounter = 1L;
    @Override
    public Faculty add(Faculty faculty) {
        faculties.put(idCounter++, faculty);
        return faculty;
    }

    @Override
    public Faculty remove(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.remove(id);
        }
        throw new EntityNotFoundException("Факультет с id = " + id + "не существует! ");
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            return faculties.put(faculty.getId(),faculty);
        }
        throw new EntityNotFoundException("Факультет с id = " + faculty.getId() + "не существует! ");

    }

    @Override
    public Faculty get(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        throw new EntityNotFoundException("Факультет с id = " + id + "не существует! ");

    }

    @Override
    public Collection<Faculty> getAll() {
        return faculties.values();
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        if (!StringUtils.hasText(color)) {
            throw new IncorrectArgumentException("Требуеться указать корректный цвет для поика факультета");

        }

        return faculties.values().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
