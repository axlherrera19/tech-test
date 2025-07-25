package com.aherrera.gameloft.profile_matcher.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.aherrera.gameloft.profile_matcher.domain.Campaign;
import com.aherrera.gameloft.profile_matcher.domain.Matcher;
import com.aherrera.gameloft.profile_matcher.domain.Matcher.DoesNotHave;
import com.aherrera.gameloft.profile_matcher.domain.Matcher.Has;
import com.aherrera.gameloft.profile_matcher.domain.Matcher.Level;
import com.aherrera.gameloft.profile_matcher.domain.PlayerProfile;

class PlayerProfileServiceTest {

    @Nested
    @DisplayName("checkInventoryItems tests")
    class CheckInventoryItemsTests {

        private PlayerProfileService service;

        @BeforeEach
        void setUp() {
            service = new PlayerProfileService();
        }

        @Test
        @DisplayName("Should return true if player inventory contains at least one campaign item")
        void checkInventoryItems_OneMatch() {
            List<String> campaignItems = Arrays.asList("Sword", "Potion");
            Map<String, Integer> playerInventory = new HashMap<>(Map.of("Axe", 1, "Sword", 1, "Shield", 1));
            assertTrue(service.checkInventoryItems(campaignItems, playerInventory));
        }

        @Test
        @DisplayName("Should return true if player inventory contains multiple campaign items")
        void checkInventoryItems_MultipleMatches() {
            List<String> campaignItems = Arrays.asList("Sword", "Potion");
            Map<String, Integer> playerInventory = new HashMap<>(Map.of("Axe", 1, "Sword", 1, "Potion", 5));
            assertTrue(service.checkInventoryItems(campaignItems, playerInventory));
        }

        @Test
        @DisplayName("Should return false if player inventory contains no campaign items")
        void checkInventoryItems_NoMatch() {
            List<String> campaignItems = Arrays.asList("Sword", "Potion");
            Map<String, Integer> playerInventory = new HashMap<>(Map.of("Axe", 1, "Shield", 1));
            assertFalse(service.checkInventoryItems(campaignItems, playerInventory));
        }

        @Test
        @DisplayName("Should return false if campaign items list is empty")
        void checkInventoryItems_EmptyCampaignItems() {
            List<String> campaignItems = Collections.emptyList();
            Map<String, Integer> playerInventory = new HashMap<>(Map.of("Sword", 1));
            assertFalse(service.checkInventoryItems(campaignItems, playerInventory));
        }

        @Test
        @DisplayName("Should return false if player inventory is empty")
        void checkInventoryItems_EmptyPlayerInventory() {
            List<String> campaignItems = Arrays.asList("Sword");
            Map<String, Integer> playerInventory = Collections.emptyMap();
            assertFalse(service.checkInventoryItems(campaignItems, playerInventory));
        }

        @Test
        @DisplayName("Should return false if both lists are empty")
        void checkInventoryItems_BothEmpty() {
            List<String> campaignItems = Collections.emptyList();
            Map<String, Integer> playerInventory = Collections.emptyMap();
            assertFalse(service.checkInventoryItems(campaignItems, playerInventory));
        }

        @Test
        @DisplayName("Should return false for case-sensitive mismatch")
        void checkInventoryItems_CaseSensitiveNoMatch() {
            List<String> campaignItems = Arrays.asList("Sword");
            Map<String, Integer> playerInventory = new HashMap<>(Map.of("sword", 1));
            assertFalse(service.checkInventoryItems(campaignItems, playerInventory));
        }

        @Test
        @DisplayName("Should return true for exact case-sensitive match")
        void checkInventoryItems_CaseSensitiveMatch() {
            List<String> campaignItems = Arrays.asList("SWORD");
            Map<String, Integer> playerInventory = new HashMap<>(Map.of("SWORD", 1));
            assertTrue(service.checkInventoryItems(campaignItems, playerInventory));
        }

        @Test
        @DisplayName("Should return false if campaignItems list is null")
        void checkInventoryItems_NullCampaignItems() {
            Map<String, Integer> playerInventory = new HashMap<>(Map.of("Sword", 1));
            assertFalse(service.checkInventoryItems(null, playerInventory));
        }

        @Test
        @DisplayName("Should return false if playerInventory map is null")
        void checkInventoryItems_NullPlayerInventory() {
            List<String> campaignItems = Arrays.asList("Sword");
            assertFalse(service.checkInventoryItems(campaignItems, null));
        }
    }

    // ---
    // Test for mustAssignCampaign method
    // ---
    @Nested
    @DisplayName("mustAssignCampaign tests")
    class MustAssignCampaignTests {

        private Campaign campaign;
        private PlayerProfile player;

        private PlayerProfileService service;

        @BeforeEach
        void setUp() {
            service = new PlayerProfileService();

            campaign = new Campaign();

            campaign.setEnabled(true);
            campaign.setGame("Test Game");
            campaign.setName("Test Campaign");
            campaign.setPriority(2.2);

            Level level = new Level(1, 10);

            Matcher matcher = new Matcher();
            matcher.setLevel(level);
            Has has = new Has();
            has.setCountry(Arrays.asList("US", "CA"));
            has.setItems(Arrays.asList("Sword", "Potion"));
            matcher.setHas(has);
            DoesNotHave doesNotHave = new DoesNotHave();
            doesNotHave.setItems(Arrays.asList("BadItem", "CursedRelic"));
            matcher.setDoesNotHave(doesNotHave);
            campaign.setMatchers(matcher);

            Map<String, Integer> inventory = new HashMap<>();
            inventory.put("Sword", 1);
            inventory.put("Potion", 21);
            inventory.put("Spike", 2);

            player = new PlayerProfile();
            player.setCountry("CA");
            player.setGender("male");
            player.setCredential("apple_credential");
            player.setLanguage("fr");
            player.setTotalPlaytime(144);
            player.setXp(1000);
            player.setLevel(3);
            player.setTotalSpent(400.0);
            player.setTotalRefund(0.0);
            player.setTotalTransactions(5);
            player.setInventory(inventory);
            player.setPlayerId(UUID.fromString("97983be2-98b7-11e7-90cf-082e5f28d836"));

        }

        // --- All Conditions Met ---
        @Test
        @DisplayName("Should assign campaign when all conditions are met")
        void mustAssignCampaign_AllConditionsMet() {
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        // --- Level Matching Tests ---
        @Test
        @DisplayName("Should assign campaign if player level is within range")
        void mustAssignCampaign_LevelMatches() {
            player.setLevel(5);
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should not assign campaign if player level is below min range")
        void mustAssignCampaign_LevelBelowMin() {
            player.setLevel(0);
            assertFalse(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should not assign campaign if player level is above max range")
        void mustAssignCampaign_LevelAboveMax() {
            player.setLevel(11);
            assertFalse(service.mustAssignCampaign(campaign, player));
        }

        // --- Has Country Matching Tests ---
        @Test
        @DisplayName("Should assign campaign if player country matches a required country")
        void mustAssignCampaign_CountryMatches() {
            player.setCountry("US");
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should not assign campaign if player country does not match required countries")
        void mustAssignCampaign_CountryNoMatch() {
            player.setCountry("ME");
            assertFalse(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should assign campaign if has country list is empty")
        void mustAssignCampaign_EmptyHasCountryList() {
            campaign.getMatchers().getHas().setCountry(Collections.emptyList());
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should assign campaign if has country list is null")
        void mustAssignCampaign_NullHasCountryList() {
            campaign.getMatchers().getHas().setCountry(null);
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should not assign campaign if player country is null but campaign requires specific countries")
        void mustAssignCampaign_PlayerCountryNullButCampaignRequiresCountry() {
            player.setCountry(null);
            campaign.getMatchers().getHas().setCountry(Arrays.asList("USA"));
            assertFalse(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should assign campaign if has items list is empty")
        void mustAssignCampaign_EmptyHasItemsList() {
            campaign.getMatchers().getHas().setItems(Collections.emptyList());
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should assign campaign if has items list is null")
        void mustAssignCampaign_NullHasItemsList() {
            campaign.getMatchers().getHas().setItems(null);
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should not assign campaign if player inventory is null but campaign requires items")
        void mustAssignCampaign_PlayerInventoryNullHasItems() {
            player.setInventory(null);
            campaign.getMatchers().getHas().setItems(Arrays.asList("Sword"));
            assertFalse(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should assign campaign if doesNotHave items list is empty")
        void mustAssignCampaign_EmptyDoesNotHaveItemsList() {
            campaign.getMatchers().getDoesNotHave().setItems(Collections.emptyList());
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should assign campaign if doesNotHave items list is null")
        void mustAssignCampaign_NullDoesNotHaveItemsList() {
            campaign.getMatchers().getDoesNotHave().setItems(null);
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        // --- Edge Cases & Null Handling for mustAssignCampaign ---
        @Test
        @DisplayName("Should return false if player profile is null")
        void mustAssignCampaign_NullPlayerProfile() {
            assertFalse(service.mustAssignCampaign(campaign, null));
        }

        @Test
        @DisplayName("Should return false if campaign is null")
        void mustAssignCampaign_NullCampaign() {
            assertFalse(service.mustAssignCampaign(null, player));
        }

        @Test
        @DisplayName("Should assign campaign if campaign has no matchers defined")
        void mustAssignCampaign_NoMatchersDefined() {
            campaign.setMatchers(null);
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should return true if 'has' matchers are null")
        void mustAssignCampaign_NullHasMatchers() {
            campaign.getMatchers().setHas(null);
            assertTrue(service.mustAssignCampaign(campaign, player));
        }

        @Test
        @DisplayName("Should return true if 'does not have' matchers are null")
        void mustAssignCampaign_NullDoesNotHaveMatchers() {
            campaign.getMatchers().setDoesNotHave(null);
            assertTrue(service.mustAssignCampaign(campaign, player));
        }
    }
}
