package com.sangboak.core.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostContentValidatorTest {

    @Getter
    @Setter
    @AllArgsConstructor
    class MockDto {
        @AllowedContent
        private String content;
    }

    @Autowired
    private Validator validatorInjected;

    @Test
    void contentIsNotAllowedWithoutWhitespacesByWords() {
        // Given
        String content = "나쁜말고운말바른말";
        MockDto mockDto = new MockDto(content);

        // When
        Set<ConstraintViolation<MockDto>> violations = validatorInjected.validate(mockDto);

        // Then
        assertThat(violations).isNotEmpty();
    }

    @Test
    void contentIsNotAllowedWithWhitespacesByWords() {
        // Given
        String content = "나쁜말 고운말 바른말";
        MockDto mockDto = new MockDto(content);

        // When
        Set<ConstraintViolation<MockDto>> violations = validatorInjected.validate(mockDto);

        // Then
        assertThat(violations).isNotEmpty();
    }

    @Test
    void contentIsNotAllowedWithWhitespacesByRegex() {
        // Given
        String content = "나쁜말 고운말 바른말 https://m.blog.naver.com/dance0616/221296121297";
        MockDto mockDto = new MockDto(content);

        // When
        Set<ConstraintViolation<MockDto>> violations = validatorInjected.validate(mockDto);

        // Then
        assertThat(violations).isNotEmpty();
    }


    @Test
    void contentIsNotAllowedWithLinkByRegex() {
        // Given
        String content = "asdf<br/>\r\nafwefawef<br/>\r\n<a href=\"https://www.naver.com\">네이버</a>";
        MockDto mockDto = new MockDto(content);

        // When
        Set<ConstraintViolation<MockDto>> violations = validatorInjected.validate(mockDto);

        // Then
        assertThat(violations).isNotEmpty();
    }


    @Test
    void contentIsAllowedWithWeirdCombinationByRegex() {
        // Given
        String content = "asdf<br/>\r\nafwefawef<br/>\r\nhttps://www.naver.com";
        MockDto mockDto = new MockDto(content);

        // When
        Set<ConstraintViolation<MockDto>> violations = validatorInjected.validate(mockDto);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void contentIsNotAllowedWithMultiline() {
        // Given
        String newLine = System.getProperty("line.separator");


        String content = "나쁜말 고운말 바른말"
                .concat(newLine)
                .concat("https://www.naver.com/")
                .concat(newLine)
                .concat("Other...");

        MockDto mockDto = new MockDto(content);

        // When
        Set<ConstraintViolation<MockDto>> violations = validatorInjected.validate(mockDto);

        // Then
        assertThat(violations).isNotEmpty();
    }

    @Test
    void contentIsNotAllowedWithMultiline2() {
        // Given
        String newLine = System.getProperty("line.separator");


        String content = "고운말 바른말"
                .concat(newLine)
                .concat("<a href=\"https://www.naver.com/\">naver</a>")
                .concat(newLine)
                .concat("Other...");

        MockDto mockDto = new MockDto(content);

        // When
        Set<ConstraintViolation<MockDto>> violations = validatorInjected.validate(mockDto);

        // Then
        assertThat(violations).isNotEmpty();
    }

    @Test
    void contentIsAllowedWithMultiline() {
        // Given
        String newLine = System.getProperty("line.separator");


        String content = "고운말 바른말"
                .concat(newLine)
                .concat("https://www.naver.com/\">naver</a>")
                .concat(newLine)
                .concat("Other...");

        MockDto mockDto = new MockDto(content);

        // When
        Set<ConstraintViolation<MockDto>> violations = validatorInjected.validate(mockDto);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void contentIsAllowed() {
        // Given
        String content = "고운말바른말 좋은말 good words";
        MockDto mockDto = new MockDto(content);

        // When
        Set<ConstraintViolation<MockDto>> violations = validatorInjected.validate(mockDto);

        // Then
        assertThat(violations).isEmpty();
    }
}
