package com.elliecat.postgresdb.repositories;

import com.elliecat.postgresdb.TestDataUtil;
import com.elliecat.postgresdb.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(final AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndFound() {

        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorEntityB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);
        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result)
                .containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {

        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorEntityA);
        authorEntityA.setName("UPDATED");
        underTest.save(authorEntityA);
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntityA);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {

        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorEntityA);
        underTest.deleteById(authorEntityA.getId());
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testAuthorsWithAgeLessThan() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA(); // 21
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorEntityB(); // 47
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC(); // 0
        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.ageLessThan(40);
        assertThat(result)
                .containsExactly(authorEntityA, authorEntityC);
    }

//    @Test
//    public void testAuthorsWithAgeGreaterThan() {
//        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA(); // 21
//        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB(); // 47
//        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC(); // 0
//        underTest.save(authorEntityA);
//        underTest.save(authorEntityB);
//        underTest.save(authorEntityC);
//
//        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(40);
//        assertThat(result)
//                .containsExactly(authorEntityB);
//    }
}
