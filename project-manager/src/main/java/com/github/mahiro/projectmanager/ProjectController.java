package com.github.mahiro.projectmanager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "http://localhost:3000")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<Project> getAll() {
        return service.getProjectList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable int id) {
        Project project = service.getProjectDetail(id);
        if (project == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProjectRequest req) {
        service.registerProject(
                req.getTitle(), req.getClientName(), req.getRequiredSkills(),
                req.getLocation(), req.getPriceMin(), req.getPriceMax());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody ProjectRequest req) {
        service.updateProject(
                id, req.getTitle(), req.getClientName(), req.getRequiredSkills(),
                req.getLocation(), req.getPriceMin(), req.getPriceMax(), req.getStatus());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
