package com.vitech.moodfeed.message.dto;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class RequestTest {

    @ParameterizedTest
    @MethodSource(value = "testExtractHashTagsSource")
    public void testExtractHashTags(String text, String expectedTags) {
        MatcherAssert.assertThat(
                Request.builder().body(text).build().extractHashTags(),
                containsInAnyOrder(StringUtils.split(expectedTags, ","))
        );
    }

    private static Stream<Arguments> testExtractHashTagsSource() {
        return Stream.of(
                Arguments.of("Some text #tag1 or #tag__2 #t_.-op", "tag1,tag__2,t_.-op")
                , Arguments.of("#Some text tag1 or tag__2 or #___tag3___", "Some,___tag3___")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "testExtractMentionedUsersSource")
    public void testExtractMentionedUsers(String text, String expectedUserNames) {
        assertThat(
                Request.builder().body(text).build().extractMentionedUsers(),
                containsInAnyOrder(StringUtils.split(expectedUserNames, ","))
        );
    }

    private static Stream<Arguments> testExtractMentionedUsersSource() {
        return Stream.of(
                Arguments.of("Some text #jack or @sam", "sam")
                , Arguments.of("@Some text tag1 or tag__2 or @_LISA_", "Some,_LISA_")
        );
    }
}