package com.aherrera.gameloft.profile_matcher.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Clan {
    
    @Id
    @Column(name = "id")
    private Long id;

    private String name;
}
