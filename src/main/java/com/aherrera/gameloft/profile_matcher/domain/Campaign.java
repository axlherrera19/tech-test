package com.aherrera.gameloft.profile_matcher.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "campaign")
@AllArgsConstructor
@NoArgsConstructor
public class Campaign implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String game;

    private String name;

    private Double priority;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "matcher_id")
    private Matcher matchers;

    @JsonProperty("start_date")
    private OffsetDateTime startDate;

    @JsonProperty("end_date")
    private OffsetDateTime endDate;

    private Boolean enabled;

    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;
}
