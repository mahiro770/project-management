package com.github.mahiro.projectmanager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbc;

    public ProjectRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Project> rowMapper = (rs, rowNum) -> new Project(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("client_name"),
            rs.getString("required_skill"),
            rs.getString("location"),
            rs.getInt("price_min"),
            rs.getInt("price_max"),
            rs.getString("status"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at"));

    public List<Project> findAll() {
        return jdbc.query("SELECT * FROM projects ORDER BY id", rowMapper);
    }

    public Project findById(int id) {
        List<Project> result = jdbc.query(
                "SELECT * FROM projects WHERE id = ?", rowMapper, id);
        return result.isEmpty() ? null : result.get(0);
    }

    public void insert(Project project) {
        jdbc.update(
                "INSERT INTO projects (title, client_name, required_skill, location, price_min, price_max) VALUES (?, ?, ?, ?, ?, ?)",
                project.getTitle(),
                project.getClientName(),
                project.getRequiredSkills(),
                project.getLocation(),
                project.getPriceMin(),
                project.getPriceMax());
    }

    public void update(Project project) {
        jdbc.update(
                "UPDATE projects SET title = ?, client_name = ?, required_skill = ?, location = ?, price_min = ?, price_max = ?, status = ?, updated_at = ? WHERE id = ?",
                project.getTitle(),
                project.getClientName(),
                project.getRequiredSkills(),
                project.getLocation(),
                project.getPriceMin(),
                project.getPriceMax(),
                project.getStatus(),
                new Timestamp(System.currentTimeMillis()),
                project.getId());
    }

    public boolean delete(int id) {
        return jdbc.update("DELETE FROM projects WHERE id = ?", id) > 0;
    }
}
