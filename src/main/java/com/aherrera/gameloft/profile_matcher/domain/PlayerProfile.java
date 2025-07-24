package com.aherrera.gameloft.profile_matcher.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.aherrera.gameloft.profile_matcher.utils.InventoryToMapConverter;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    /**
     *  I assume the inventory is a MAP, where the key is the name of the item, and the value is the numeric stock value.
     *  As I'm using SQL, I'm gonna save it into a TEXT column, and then, when serializing to JAVA object, I'm converting it into a map.
     */
    @JsonProperty("inventory")
    @Convert(converter = InventoryToMapConverter.class)
    @Column(name = "inventory", columnDefinition = "TEXT")
    private Map<String, Integer> inventory;

    @OneToOne
    private Clan clan;

    @Column(name = "custom_field")
    @JsonProperty("_customfield")
    private String customField;
}
