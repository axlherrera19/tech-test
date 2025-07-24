package com.aherrera.gameloft.profile_matcher.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "player_profile")
public class PlayerProfile implements Serializable {

    @Id
    @JsonProperty("player_id")
    private UUID playerId;

    private String credential;

    @JsonProperty("created")
    private OffsetDateTime createdAt;

    @JsonProperty("modified")
    private OffsetDateTime modifiedAt;

    @JsonProperty("last_session")
    private OffsetDateTime lastSession;

    @JsonProperty("total_spent")
    private Double totalSpent;

    @JsonProperty("total_refund")
    private Double totalRefund;

    @JsonProperty("total_transactions")
    private Integer totalTransactions;

    @JsonProperty("last_purchase")
    private OffsetDateTime lastPurchase;

    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "player_profile_id")
    private Set<Campaign> activeCampaigns;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "player_id")
    private Set<Device> devices;

    private Integer level;

    private Integer xp;

    @JsonProperty("total_playtime")
    private Integer totalPlaytime;

    private String country;

    private String language;

    @JsonProperty("birthdate")
    private OffsetDateTime birthDate; // Renamed to birthDate for clarity

    private String gender;

    //I don't consider Inventory has enough weigh to become an entity on its own.
    // Embedded to reduce joins
    @Embedded 
    private Inventory inventory;

    @OneToOne
    private Clan clan;

    @Column(name = "custom_field")
    @JsonProperty("_customfield")
    private String customField;


    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Inventory {

            private Integer cash;
            private Integer coins;
            private Integer item_1;
            private Integer item_34;
            private Integer item_55;

    }
}
