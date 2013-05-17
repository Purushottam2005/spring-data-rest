package org.emaginalabs.example.springdata.service;

import org.emaginalabs.example.springdata.domain.Todo;
import org.emaginalabs.example.springdata.exception.TodoNotFoundException;
import org.emaginalabs.example.springdata.response.TodoDto;

import java.util.List;

/**
 * User: jaluque
 * Date: 16/05/13
 * Time: 16:15
 */
public interface TodoService {

        /**
         * Adds a new to-do entry.
         * @param added The information of the added to-do entry.
         * @return  The added to-do entry.
         */
        Todo add(TodoDto added);

        /**
         * Deletes a to-do entry.
         * @param id    The id of the deleted to-do entry.
         * @return  The deleted to-do entry.
         * @throws TodoNotFoundException    if no to-do entry is found with the given id.
         */
        Todo deleteById(Long id) throws TodoNotFoundException;

        /**
         * Returns a list of to-do entries.
         * @return
         */
        List<Todo> findAll();

        /**
         * Finds a to-do entry.
         * @param id    The id of the wanted to-do entry.
         * @return  The found to-entry.
         * @throws TodoNotFoundException    if no to-do entry is found with the given id.
         */
        Todo findById(Long id) throws TodoNotFoundException;

        /**
         * Updates the information of a to-do entry.
         * @param updated   The information of the updated to-do entry.
         * @return  The updated to-do entry.
         * @throws TodoNotFoundException    If no to-do entry is found with the given id.
         */
        Todo update(TodoDto updated) throws TodoNotFoundException;

        /**
         * Searches the todo entries which title or description contains the given search term.
         * @param searchTerm
         * @return The list of todo entries. If matching todo entries are not found, the method returns an empty list.
         */
        List<Todo> search(String searchTerm);
}