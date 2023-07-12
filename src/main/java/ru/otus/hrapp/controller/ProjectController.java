package ru.otus.hrapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hrapp.service.ProjectService;

@AllArgsConstructor
@RestController
public class ProjectController {

    private final ProjectService projectService;
}
