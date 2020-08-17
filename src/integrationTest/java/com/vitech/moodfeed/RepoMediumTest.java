package com.vitech.moodfeed;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJdbcTest
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RepoMediumTest<T> extends MediumTest {

    private final CrudRepository<T, Long> crudRepository;

    @Test
    void testCRUD() {
        // store new entity
        T storedEntity = crudRepository.save(entityProvider());
        assertEquals(Optional.of(storedEntity), crudRepository.findById(idProvider(storedEntity)));
        // update stored entity
        T updatedEntity = updatedEntityProvider(idProvider(storedEntity));
        crudRepository.save(updatedEntity);
        assertEquals(Optional.of(updatedEntity), crudRepository.findById(idProvider(storedEntity)));
        // delete stored entity
        crudRepository.deleteById(idProvider(storedEntity));
        assertFalse(crudRepository.findById(idProvider(storedEntity)).isPresent());
    }

    protected abstract T entityProvider();

    protected abstract T updatedEntityProvider(Long id);

    protected abstract Long idProvider(T entity);

}
