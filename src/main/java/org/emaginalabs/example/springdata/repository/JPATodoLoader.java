package org.emaginalabs.example.springdata.repository;

import org.emaginalabs.example.springdata.domain.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * User: jaluque
 * Date: 14/05/13
 * Time: 12:14
 */
public class JPATodoLoader {

    private final static Logger LOG = LoggerFactory.getLogger(JPATodoLoader.class);

    @Autowired
     private TodoRepository todoRepository;

    @Transactional
    public void loadData() {
        Todo todo = new Todo();
        todo.setTitle("Todo 1");
        todo.setDescription("Todo details 1");
        todo.setDue(new Date());
        todo.setReminderme(new Date());
        todoRepository.save(todo);

        LOG.info("Saved {}", todo);

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");
        todo2.setDescription("Todo details 2");
        todo2.setDue(new Date());
        todo2.setReminderme(new Date());
        todoRepository.save(todo2);

        Todo todo3 = new Todo();
        todo3.setTitle("Todo 3");
        todo3.setDescription("Todo details 3");
        todo3.setDue(new Date());
        todo3.setReminderme(new Date());
        todoRepository.save(todo3);

        LOG.info("Todo count: {}", todoRepository.count());
    }

    public void unloadData() {
        LOG.debug("Destroy info");
    }

}
