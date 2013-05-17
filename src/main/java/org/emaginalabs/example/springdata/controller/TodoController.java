package org.emaginalabs.example.springdata.controller;

import org.emaginalabs.example.springdata.domain.Todo;
import org.emaginalabs.example.springdata.exception.FormValidationError;
import org.emaginalabs.example.springdata.exception.TodoNotFoundException;
import org.emaginalabs.example.springdata.repository.TodoRepository;
import org.emaginalabs.example.springdata.response.TodoDto;
import org.emaginalabs.example.springdata.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

    protected static final String OBJECT_NAME_TODO = "todo";
	

	@Autowired 
	private TodoService service;

    @Resource
    private MessageSource messageSource;

    @Resource
    private Validator validator;

    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    @ResponseBody
    public TodoDto add(@RequestBody TodoDto dto) throws FormValidationError {
        LOGGER.debug("Adding a new to-do entry with information: {}", dto);

        validate(OBJECT_NAME_TODO, dto);

        Todo added = service.add(dto);
        LOGGER.debug("Added a to-do entry with information: {}", added);

        return createDTO(added);
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public TodoDto deleteById(@PathVariable("id") Long id) throws TodoNotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Todo deleted = service.deleteById(id);

        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return createDTO(deleted);
    }

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    @ResponseBody
    public List<TodoDto> findAll() {
        LOGGER.debug("Finding all todo entries.");

        List<Todo> models = service.findAll();
        LOGGER.debug("Found {} to-do entries.", models.size());

        return createDTOs(models);
    }


    @RequestMapping(value = "/todo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TodoDto findById(@PathVariable("id") Long id) throws TodoNotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Todo found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return createDTO(found);
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public TodoDto update(@RequestBody TodoDto dto, @PathVariable("id") Long todoId) throws TodoNotFoundException, FormValidationError {
        LOGGER.debug("Updating a to-do entry with information: {}", dto);

        validate(OBJECT_NAME_TODO, dto);

        Todo updated = service.update(dto);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return createDTO(updated);
    }



    private void validate(String objectName, Object validated) throws FormValidationError {
        LOGGER.debug("Validating object: " + validated);

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(validated, objectName);
        validator.validate(validated, bindingResult);

        if (bindingResult.hasErrors()) {
            LOGGER.debug("Validation errors found:" + bindingResult.getFieldErrors());
            throw new FormValidationError(bindingResult.getFieldErrors());
        }
    }

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFoundException(TodoNotFoundException ex) {
        LOGGER.debug("handling 404 error on a todo entry");
    }
    private TodoDto createDTO(Todo model) {

        TodoDto dto = new TodoDto();
        dto.setId(model.getId());
        dto.setDescription(model.getDescription());
        dto.setTitle(model.getTitle());

        return dto;
    }
    private List<TodoDto> createDTOs(List<Todo> models) {
        List<TodoDto> dtos = new ArrayList<TodoDto>();

        for (Todo model: models) {
            dtos.add(createDTO(model));
        }

        return dtos;
    }
}
