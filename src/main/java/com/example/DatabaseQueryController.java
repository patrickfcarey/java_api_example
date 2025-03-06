package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DatabaseQueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/query")
    public List<Map<String, Object>> queryPerson(@RequestBody String jsonInput) {
        try {
            // Log incoming request
            System.out.println("Received JSON: " + jsonInput);

            // Test database connection explicitly
            try {
                jdbcTemplate.execute("SELECT 1"); // Simple query to test connectivity
                System.out.println("Database connection successful");
            } catch (Exception e) {
                throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Database connection failed: " + e.getMessage(),
                    e
                );
            }

            // Parse JSON input
            JsonNode jsonNode = mapper.readTree(jsonInput);
            String searchName = jsonNode.get("name").asText();
            System.out.println("Searching for name: " + searchName);

            // Execute query
            String sql = "SELECT * FROM person WHERE LOWER(name) = LOWER(?)";
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, searchName);
            System.out.println("Query results: " + results);

            // Return results or empty list if no matches
            return results.isEmpty() ? new ArrayList<>() : results;

        } catch (ResponseStatusException e) {
            // Re-throw connection-specific exceptions
            throw e;
        } catch (Exception e) {
            // Handle JSON parsing or other errors
            System.err.println("Error in queryPerson: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Error processing request: " + e.getMessage(),
                e
            );
        }
    }
}