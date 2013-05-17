package org.emaginalabs.example.springdata.service.impl;

import org.emaginalabs.example.springdata.domain.Todo;
import org.emaginalabs.example.springdata.exception.TodoNotFoundException;
import org.emaginalabs.example.springdata.repository.TodoRepository;
import org.emaginalabs.example.springdata.response.TodoDto;
import org.emaginalabs.example.springdata.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {


    private static final Logger LOGGER = LoggerFactory.getLogger(TodoServiceImpl.class);


    @Resource
    private TodoRepository repository;

    @PreAuthorize("hasPermission('Todo', 'add')")
    @Transactional
    @Override
    public Todo add(TodoDto added) {
        LOGGER.debug("Adding a new to-do entry with information: {}", added);

        Todo model = Todo.getBuilder(added.getTitle())
                .description(added.getDescription())
                .build();

        Todo persisted = repository.save(model);

        return persisted;
    }

    @PreAuthorize("hasPermission('Todo', 'delete')")
    @Transactional(rollbackFor = {TodoNotFoundException.class})
    @Override
    public Todo deleteById(Long id) throws TodoNotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Todo deleted = findById(id);
        LOGGER.debug("Deleting to-do entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @PreAuthorize("hasPermission('Todo', 'list')")
    @Transactional(readOnly = true)
    @Override
    public List<Todo> findAll() {
        LOGGER.debug("Finding all to-do entries");
        return repository.findAll();
    }

    @PreAuthorize("hasPermission('Todo', 'find')")
    @Transactional(readOnly = true, rollbackFor = {TodoNotFoundException.class})
    @Override
    public Todo findById(Long id) throws TodoNotFoundException {
        LOGGER.debug("Finding a to-do entry with id: {}", id);

        Todo found = repository.findOne(id);
        LOGGER.debug("Found to-do entry: {}", found);

        if (found == null) {
            throw new TodoNotFoundException("No to-entry found with id: " + id);
        }

        return found;
    }

    @PreAuthorize("hasPermission('Todo', 'update')")
    @Transactional(rollbackFor = {TodoNotFoundException.class})
    @Override
    public Todo update(TodoDto updated) throws TodoNotFoundException {
        LOGGER.debug("Updating todo entry with information: {}", updated);

        Todo model = findById(updated.getId());
        LOGGER.debug("Found a to-do entry: {}", model);

        model.update(updated.getDescription(), updated.getTitle());

        return model;
    }

    @PreAuthorize("hasPermission('Todo', 'search')")
    @Override
    public List<Todo> search(String searchTerm) {
        LOGGER.debug("Search todo entries with search term: {}", searchTerm);
        return new ArrayList<Todo>();
    }
}
