package com.todobackend.mohsushi.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/todos")
public class TodoBackendController {

  private final TodoBackendRepository todoBackendRepository;

  public TodoBackendController(TodoBackendRepository todoBackendRepository) {
    this.todoBackendRepository = todoBackendRepository;
  }

  @GetMapping
  public List<TodoBackendEntry> getAll() {
//    final List<TodoBackendEntry> all = new ArrayList<>();
//    todoBackendRepository.findAll().forEach(i -> all.add(i));
//    return all;
    return StreamSupport.stream(todoBackendRepository.findAll().spliterator(), false).collect(Collectors.toList());
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteAll() {
    todoBackendRepository.deleteAll();
    return ResponseEntity.ok().build();
  }


  // ---------------------------------------------------------------- entries


  @PostMapping
  public TodoBackendEntry create(@RequestBody @Valid TodoBackendEntry entry, HttpServletRequest httpServletRequest) {
    entry.setUrl(httpServletRequest.getRequestURL().toString());
    return todoBackendRepository.save(entry);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<TodoBackendEntry> getById(@PathVariable final long id) {
    return ResponseEntity.of(todoBackendRepository.findById(id));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<TodoBackendEntry> deleteEntry(@PathVariable final long id) {
    final Optional<TodoBackendEntry> entry = todoBackendRepository.findById(id);
    todoBackendRepository.deleteById(id);
    return ResponseEntity.of(entry);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<TodoBackendEntry> update (@PathVariable final long id, @RequestBody TodoBackendEntry entry) {
    // TODO think about moving logic into separate layer
    Optional<TodoBackendEntry> entryFromDb = todoBackendRepository.findById(id);
    if (entryFromDb.isEmpty()) return ResponseEntity.of(entryFromDb);
    // patch given entry from db with given one
    if (entry.getTitle() != null) {
      entryFromDb.get().setTitle(entry.getTitle());
    }
    if (entry.getCompleted() != null) {
      entryFromDb.get().setCompleted(entry.getCompleted());
    }
    if (entry.getOrder() != null) {
      entryFromDb.get().setOrder(entry.getOrder());
    }
    // update entry
    todoBackendRepository.save(entryFromDb.get());

    return ResponseEntity.ok(entryFromDb.get());
  }

}
