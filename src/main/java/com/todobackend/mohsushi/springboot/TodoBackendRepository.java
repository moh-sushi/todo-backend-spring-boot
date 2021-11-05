package com.todobackend.mohsushi.springboot;

import org.springframework.data.repository.CrudRepository;

public interface TodoBackendRepository extends CrudRepository<TodoBackendEntry, Long> {
}
