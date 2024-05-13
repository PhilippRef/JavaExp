package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import main.repository.Task;
import main.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {
    private final TaskRepository taskRepository;

    public Controller(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping(value = "/tasks", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        task.setCreationTime(new Date());
        task.setDone(false);
        taskRepository.saveAndFlush(task);

        return ResponseEntity.ok("201");
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity getTaskById(@PathVariable int id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404");
        }
        return new ResponseEntity(taskOptional, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        Iterable<Task> taskIterable = taskRepository.findAll();

        List<Task> tasks = new ArrayList<>();
        for (Task task : taskIterable) {
            tasks.add(task);
        }
        return tasks;
    }

    @PatchMapping(value = "/tasks/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<String> updateTaskFields(@PathVariable int id,
                                                   @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Task task = taskRepository.findById(id).get();
        Task taskPatch = applyPatchToTask(patch, task);
        taskRepository.save(taskPatch);
        return ResponseEntity.ok(taskPatch.toString());
    }

    private Task applyPatchToTask(JsonPatch patch, Task task) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(task, JsonNode.class));
        return objectMapper.treeToValue(patched, Task.class);
    }

    @DeleteMapping(path = "/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") int id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404");
        }
        taskRepository.deleteById(id);
        return ResponseEntity.ok("200");
    }
}
