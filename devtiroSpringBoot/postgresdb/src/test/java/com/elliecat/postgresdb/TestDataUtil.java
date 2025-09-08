package com.elliecat.postgresdb;

import com.elliecat.postgresdb.domain.Author;
import com.elliecat.postgresdb.domain.Book;

public final class TestDataUtil {

    private TestDataUtil() {}

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Audrie Sager")
                .age(21)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Hunter Pitt")
                .age(21)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Sailor")
                .age(0)
                .build();
    }
    public static Book createTestBookA() {
        return Book.builder()
                .isbn("1234")
                .title("Magic Island")
                .authorId(1L)
                .build();
    }
    public static Book createTestBookB() {
        return Book.builder()
                .isbn("3456")
                .title("Memoirs")
                .authorId(1L)
                .build();
    }
    public static Book createTestBookC() {
        return Book.builder()
                .isbn("5678")
                .title("My Poems")
                .authorId(1L)
                .build();
    }
}
