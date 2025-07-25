package com.aherrera.gameloft.profile_matcher.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aherrera.gameloft.profile_matcher.domain.Campaign;
import com.aherrera.gameloft.profile_matcher.domain.PlayerProfile;
import com.aherrera.gameloft.profile_matcher.repository.PlayerProfileRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlayerProfileService {

    @Autowired
    private PlayerProfileRepository playerProfileRepository;

    /**
     * Forcing CampaignHTTPService here to recover RunningCampaigns from API
     */
    @Autowired
    @Qualifier("campaignHttpService")
    private ICampaignService campaignService;

    public PlayerProfile getClientConfigById(String playerId) {

        PlayerProfile playerProfile = null;
        if (playerId != null && UUID.fromString(playerId) != null) {
            Optional<PlayerProfile> playerProfileOpt = playerProfileRepository.findById(UUID.fromString(playerId));
            if (playerProfileOpt.isPresent()) {
                playerProfile = playerProfileOpt.get();
                // getRunningCampaigns NEVER returns null
                List<Campaign> runningCampaigns = campaignService.getRunningCampaigns();
                for (Campaign runningCampaign : runningCampaigns) {
                    if (mustAssignCampaign(runningCampaign, playerProfile) && runningCampaign.getName() != null
                            && !runningCampaign.getName().isEmpty()) {
                        if (playerProfile.getActiveCampaigns() == null) {
                            // Do not forget to initialize it before ADD
                            playerProfile.setActiveCampaigns(new HashSet<>(new ArrayList<>()));
                        }
                        // For this example, I'm matchin "live" campaigns with player profile. No Update
                        // in Player Profile entity in DATABASE is done.
                        // For every request to this service, running campaigns are retrieved and verify
                        // it with the user profile
                        // If player profile matches all requirements, campaign is assigned to the
                        // result entity ONLY and the modified player profile is returned, but nothing
                        // changes on DB
                        playerProfile.getActiveCampaigns().add(runningCampaign.getName());
                    }

                }

            }
        }

        return playerProfile;
    }

    /**
     * Main Algorithm. Check if the player profile matches with all settings of the
     * campaign
     * I assume all conditions must be matched, then I consider only player profiles
     * who meet all campaign requirements (AND logic)
     * must be associated to the campaign.
     * 
     * If at least one condition doesn't match, the user is not elegible for the
     * campaign
     * 
     * Checks Level of the player profile
     * Checks HAS Country
     * Checks HAS Items
     * Check DOES NOT HAVE Items
     * 
     * @param runningCampaign
     * @param playerProfile
     * @return true if the current campaing must be assigned to the user profile.
     *         False if not.
     */
    boolean mustAssignCampaign(Campaign runningCampaign, PlayerProfile playerProfile) {
        boolean levelMatches = false;
        boolean hasCountryMatches = false;
        boolean hasItemsMatches = false;
        boolean doesNotHaveItemsMatches = false;

        if (playerProfile != null && runningCampaign != null) {
            if (runningCampaign.getMatchers() != null) {
                // 1. Check Level
                if (runningCampaign.getMatchers().getLevel() != null
                        && runningCampaign.getMatchers().getLevel().getMin() != null
                        && runningCampaign.getMatchers().getLevel().getMax() != null) {
                    if (playerProfile.getLevel() <= runningCampaign.getMatchers().getLevel().getMax()
                            && playerProfile.getLevel() >= runningCampaign.getMatchers().getLevel().getMin()) {
                        // Level of player is smaller than Campaign MAX Level and bigger than Campaing
                        // MIN level --> Level is in range
                        levelMatches = true;
                    }

                }

                // 2. Check Has Matchers;
                if (runningCampaign.getMatchers().getHas() != null) {
                    // 2.1. Check HAS countries
                    if (runningCampaign.getMatchers().getHas().getCountry() != null
                            && !runningCampaign.getMatchers().getHas().getCountry().isEmpty() && runningCampaign
                                    .getMatchers().getHas().getCountry().contains(playerProfile.getCountry())) {
                        // Country ok
                        hasCountryMatches = true;
                    } else {
                        if (runningCampaign.getMatchers().getHas().getCountry() == null
                                || runningCampaign.getMatchers().getHas().getCountry().isEmpty()) {
                            // If HAS list of Countries is empty, by definition, it matches the
                            // requirement, so return true
                            hasCountryMatches = true;
                        }
                    }

                    // 2.2. Check HAS Items
                    if (runningCampaign.getMatchers().getHas().getItems() != null
                            && !runningCampaign.getMatchers().getHas().getItems().isEmpty()
                            && playerProfile.getInventory() != null) {
                        hasItemsMatches = checkInventoryItems(runningCampaign.getMatchers().getHas().getItems(),
                                playerProfile.getInventory());

                    } else {
                        if (runningCampaign.getMatchers().getHas().getItems() == null
                                || runningCampaign.getMatchers().getHas().getItems().isEmpty()) {
                            // If HAS list of items is empty, by definition, it matches the
                            // requirement, so return true
                            hasItemsMatches = true;
                        }
                    }
                } else {
                    // HAS Matchers are null --> nothing to validate --> assign the campaign
                    hasCountryMatches = true;
                    hasItemsMatches = true;
                }
                // 3. Check DOES NOT HAVE Items
                if (runningCampaign.getMatchers().getDoesNotHave() != null
                        && runningCampaign.getMatchers().getDoesNotHave().getItems() != null
                        && !runningCampaign.getMatchers().getDoesNotHave().getItems().isEmpty()
                        && playerProfile.getInventory() != null) {
                    // Important. checkInventoryItems checks if inventory items are inside provided
                    // list of items, as we are checking here that the item in inventory MUST NOT BE
                    // on getDoesNotHave().getItems(), we have to ne
                    doesNotHaveItemsMatches = !checkInventoryItems(
                            runningCampaign.getMatchers().getDoesNotHave().getItems(),
                            playerProfile.getInventory());
                } else {
                    if (runningCampaign.getMatchers().getDoesNotHave() == null
                            || runningCampaign.getMatchers().getDoesNotHave().getItems() == null ||
                            runningCampaign.getMatchers().getDoesNotHave().getItems().isEmpty()) {
                        // If Does Not HAve list of items is empty, by definition, it matches the
                        // requirement, so return true
                        doesNotHaveItemsMatches = true;
                    }
                }
            } else {
                // no matchers defined --> I assume the campaign can be assigned to any user
                levelMatches = true;
                hasCountryMatches = true;
                hasItemsMatches = true;
                doesNotHaveItemsMatches = true;
            }
        }

        // ALL requirments are met. Then, I consider the campaign must be assigned to
        // the player profile
        return levelMatches && hasCountryMatches && hasItemsMatches && doesNotHaveItemsMatches;
    }

    /**
     * Check if an required item for the campaign is included or not in the Player
     * inventory
     * 
     * @param campaignItems
     * @param playerInventory
     * @return true if at least on of the items in the campaign is in inventory.
     *         False if not found
     */
    boolean checkInventoryItems(List<String> campaignItems, Map<String, Integer> playerInventory) {
        // While, because if at least one item in inventory of player profile is
        // included as a requirement for the campaing is enough, no need to continue
        // iterating
        boolean atLeastOneFound = false;
        if (playerInventory != null && campaignItems != null) {
            Iterator<String> keysIterator = playerInventory.keySet().iterator();
            while (!atLeastOneFound && keysIterator.hasNext()) {
                String itemInventoryName = keysIterator.next();
                if (campaignItems.contains(itemInventoryName)) {
                    // At least one Item In Inventory is in DOES NOT HAVE matcher --> Items ok
                    // True for exiting the loop
                    atLeastOneFound = true;
                }
            }
        }

        return atLeastOneFound;
    }
}