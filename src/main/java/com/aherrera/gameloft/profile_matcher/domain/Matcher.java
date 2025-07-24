package com.aherrera.gameloft.profile_matcher.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "matcher")
public class Matcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Embedded objects to reduce the number of tables and joins to be done.
     * A Matcher has no sense without Level, Has and DoesNotHave and vice versa.
     */
    @Embedded
    private Level level;

    @Embedded
    private Has has;

    @JsonProperty("does_not_have")
    @Embedded
    private DoesNotHave doesNotHave;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Level {

        private Integer min;

        private Integer max;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Has {

        private List<String> country;

        @Column(name = "has_items")
        private List<String> items;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DoesNotHave {

        // Avoid duplicated columns (items) in same entity 
        @Column(name = "does_not_have_items")
        private List<String> items;
    }
}
