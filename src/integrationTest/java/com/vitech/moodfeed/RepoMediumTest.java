package com.vitech.moodfeed;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DataJdbcTest
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RepoMediumTest<T> extends MediumTest {

    private final CrudRepository<T, Long> crudRepo;
    private final CrudTestDataProvider<T> crudTest;

    @Test
    void testCRUD() {
        // store new entity
        T storedEntity = crudRepo.save(crudTest.entityProvider.get());
        Long id = crudTest.idProvider.apply(storedEntity);
        assertEquals(Optional.of(storedEntity), crudRepo.findById(id));
        // update stored entity
        T updatedEntity = crudTest.updatedEntityProvider.apply(id);
        crudRepo.save(updatedEntity);
        assertEquals(Optional.of(updatedEntity), crudRepo.findById(id));
        // delete stored entity
        crudRepo.deleteById(id);
        assertFalse(crudRepo.findById(id).isPresent());
    }

    @Test
    void testPagination() {
        // check if we need to test pagination logic
        assumeTrue(crudRepo instanceof PagingAndSortingRepository);
        PagingAndSortingRepository<T, Long> pagingSortingRepo = (PagingAndSortingRepository<T, Long>) crudRepo;
        // store few entities
        IntStream.range(0, 5).forEach((i) -> pagingSortingRepo.save(crudTest.entityProvider.get()));
        // test pagination
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<T> pageResponse = pagingSortingRepo.findAll(pageRequest);
        assertEquals(5, pageResponse.getTotalElements());
        assertEquals(2, Lists.newArrayList(pageResponse).size());
    }

    @Value
    protected static class CrudTestDataProvider<T> {

        Function<T, Long> idProvider;
        Supplier<T> entityProvider;
        Function<Long, T> updatedEntityProvider;

    }

}
