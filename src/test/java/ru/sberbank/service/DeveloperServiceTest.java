package ru.sberbank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperServiceTest {
    @Test
    void createDeveloperTest_success() {
        DeveloperService ds = new DeveloperService();
        var firstName = "Angela";
        var secondName = "Samsonova";

        ds.createDeveloper(1, firstName, secondName);
        var actualDeveloper = ds.list.get(0);

        assertFalse(ds.list.isEmpty());
        assertTrue(ds.list.contains(actualDeveloper));
        assertEquals(1, actualDeveloper.id);
        assertEquals("Angela", actualDeveloper.firstName);
        assertEquals("Samsonova", actualDeveloper.secondName);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void createDeveloperTest_exception_when_secondName_is_null_or_empty(String input) {
        DeveloperService ds = new DeveloperService();
        var secondName = "Samsonova";

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            ds.createDeveloper(1, input, secondName);
        });

        assertEquals("Входные данные не валидны", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void createDeveloperTest_exception_when_firstName_is_null_or_empty(String input) {
        DeveloperService ds = new DeveloperService();
        var firstName = "Angela";

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            ds.createDeveloper(1, firstName, input);
        });

        assertEquals("Входные данные не валидны", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Angela", "ANgELa"})
    void getDeveloperTest_success_with_or_without_case_difference(String input) {
        DeveloperService ds = new DeveloperService();
        var firstName = "Angela";
        var secondName = "Samsonova";
        ds.createDeveloper(1, firstName, secondName);

        var actualDeveloper = ds.getDeveloper(input, "Samsonova");

        assertNotNull(actualDeveloper);
        assertEquals(1, actualDeveloper.id);
        assertEquals("Angela", actualDeveloper.firstName);
        assertEquals("Samsonova", actualDeveloper.secondName);
    }

    @Test
    void getDeveloperTest_exception_with_nonexistent_developer() {
        DeveloperService ds = new DeveloperService();
        var firstName = "Angela";
        var secondName = "Samsonova";
        ds.createDeveloper(1, firstName, secondName);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            ds.getDeveloper("Angela", "Smirnova");
        });

        //почему множественное число?
        assertEquals("Разработчики не найдены", exception.getMessage());
    }

    @Test
    void getFreeDevelopersTest_success_when_developer_exists() {
        DeveloperService ds = new DeveloperService();
        var firstName = "Angela";
        var secondName = "Samsonova";
        ds.createDeveloper(1, firstName, secondName);
        var actualDeveloper = ds.getDeveloper("Angela", "Samsonova");

        var actualFreeDevelopersList = ds.getFreeDevelopers();

        assertFalse(actualFreeDevelopersList.isEmpty());
        assertTrue(actualFreeDevelopersList.contains(actualDeveloper));
        assertTrue(actualDeveloper.isFree);
    }

    @Test
    void getFreeDevelopersTest_success_when_developer_list_is_empty() {
        DeveloperService ds = new DeveloperService();

        var actualFreeDevelopersList = ds.getFreeDevelopers();

        assertTrue(actualFreeDevelopersList.isEmpty());
    }
}