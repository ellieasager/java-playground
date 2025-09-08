package com.elliecat.postgresdb;

import com.elliecat.postgresdb.domain.dto.AuthorDto;
import com.elliecat.postgresdb.domain.dto.BookDto;
import com.elliecat.postgresdb.domain.entities.AuthorEntity;
import com.elliecat.postgresdb.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil() {}

    public static AuthorEntity createTestAuthorEntityA() {
        return AuthorEntity.builder()
//                .id(1L)
                .name("Audrie Sager")
                .age(21)
                .build();
    }
    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
//                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }
    public static AuthorEntity createTestAuthorEntityB() {
        return AuthorEntity.builder()
//                .id(2L)
                .name("Ellie Sager")
                .age(47)
                .build();
    }
    public static AuthorDto createTestAuthorDtoB() {
        return AuthorDto.builder()
//                .id(2L)
                .name("Ellie Sager")
                .age(47)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
//                .id(3L)
                .name("Sailor")
                .age(0)
                .build();
    }
    public static BookEntity createTestBookEntityA(AuthorEntity author) {
        return BookEntity.builder()
                .isbn("1234")
                .title("Magic Island")
                .authorEntity(author)
                .build();
    }
    public static BookDto createTestBookDtoA(AuthorDto author) {
        return BookDto.builder()
                .isbn("1234")
                .title("Magic Island")
                .author(author)
                .build();
    }
    public static BookEntity createTestBookEntityB(AuthorEntity author) {
        return BookEntity.builder()
                .isbn("3456")
                .title("Memoirs")
                .authorEntity(author)
                .build();
    }

    public static BookDto createTestBookDtoB(AuthorDto author) {
        return BookDto.builder()
                .isbn("3456")
                .title("Memoirs")
                .author(author)
                .build();
    }
    public static BookEntity createTestBookC(AuthorEntity author) {
        return BookEntity.builder()
                .isbn("5678")
                .title("My Poems")
                .authorEntity(author)
                .build();
    }
}
