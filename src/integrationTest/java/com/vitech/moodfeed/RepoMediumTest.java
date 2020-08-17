package com.vitech.moodfeed;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJdbcTest
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RepoMediumTest<T> extends MediumTest {

    private final CrudRepository<T, Long> crudRepository;
    private final CrudTestDataProvider<T> crudTest;

    @Test
    void testCRUD() {
        // store new entity
        T storedEntity = crudRepository.save(crudTest.entityProvider.get());
        Long id = crudTest.idProvider.apply(storedEntity);
        assertEquals(Optional.of(storedEntity), crudRepository.findById(id));
        // update stored entity
        T updatedEntity = crudTest.updatedEntityProvider.apply(id);
        crudRepository.save(updatedEntity);
        assertEquals(Optional.of(updatedEntity), crudRepository.findById(id));
        // delete stored entity
        crudRepository.deleteById(id);
        assertFalse(crudRepository.findById(id).isPresent());
    }

    @Value
    protected static class CrudTestDataProvider<T> {

        Function<T, Long> idProvider;
        Supplier<T> entityProvider;
        Function<Long, T> updatedEntityProvider;

    }

}
