package ru.sberbank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {
    @Test
    void createTaskTest_success() {
        TaskService ts = new TaskService();
        var summary = "testSummary";

        ts.createTask(1, summary);
        var actual = ts.getTask(summary);

        assertNotNull(actual);
        assertEquals(1, actual.id);
        assertEquals("testSummary", actual.summary);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void createTaskTest_exception_when_summary_is_null_or_empty(String input) {
        TaskService ts = new TaskService();

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            ts.createTask(1, input);
        });

        assertEquals("Входные данные не валидны", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"testSummary", "TeSTsuMmaRy"})
    void getTaskTest_success_with_or_without_case_difference(String input) {
        TaskService ts = new TaskService();
        var summary = "testSummary";
        ts.createTask(1, summary);

        var actual = ts.getTask(input);

        assertNotNull(actual);
        assertEquals(1, actual.id);
        assertEquals("testSummary", actual.summary);
    }

    @Test
    void getTaskTest_exception_when_no_task_with_given_summary() {
        TaskService ts = new TaskService();
        var summary = "testSummary";
        var nonExistentSummary = "summaryForException";
        ts.createTask(1, summary);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            ts.getTask(nonExistentSummary);
        });

        assertEquals("Задачи не найдены", exception.getMessage());
    }

    @Test
    void getTasksForDevelopingTest_success_when_tasks_exist() {
        TaskService ts = new TaskService();
        var summary = "testSummary";
        ts.createTask(1, summary);
        var actual = ts.getTask(summary);

        var actualTasksList = ts.getTasksForDeveloping();

        assertFalse(actualTasksList.isEmpty());
        // assertTrue(actualTasks.stream().anyMatch(e -> e.equals(actual)));
        assertTrue(actualTasksList.contains(actual));
    }

    @Test
    void getTasksForDevelopingTest_success_when_arraylist_is_empty() {
        TaskService ts = new TaskService();

        var actualTasksList = ts.getTasksForDeveloping();

        assertTrue(actualTasksList.isEmpty());
    }
}