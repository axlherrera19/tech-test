package com.aherrera.gameloft.profile_matcher.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

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

    // @ElementCollection // For collections of basic types or embeddables
    // @CollectionTable(name = "player_active_campaigns", joinColumns = @JoinColumn(name = "player_id"))
    // @Column(name = "campaign_name")
    // private List<String> activeCampaigns;

    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // One player has many devices
    // @JoinColumn(name = "player_id") // Foreign key in the device table
    // private List<Device> devices;

    private Integer level;

    private Integer xp;

    @JsonProperty("total_playtime")
    private Integer totalPlaytime;

    private String country;

    private String language;

    @JsonProperty("birthdate")
    private OffsetDateTime birthDate; // Renamed to birthDate for clarity

    private String gender;

    // @Embedded // Embeds the Inventory object directly into the PlayerProfile table
    // private Inventory inventory;

    // @Embedded // Embeds the Clan object directly into the PlayerProfile table
    // private Clan clan;

    @Column(name = "custom_field") // Maps "_customfield" to "custom_field" in DB
    @JsonProperty("_customfield")
    private String customField;
}
