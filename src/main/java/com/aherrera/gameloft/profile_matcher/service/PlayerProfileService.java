package com.aherrera.gameloft.profile_matcher.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CampaignService campaignService;

    public PlayerProfile getClientConfigById(String playerId) {

        PlayerProfile playerProfile = null;
        if (playerId != null && UUID.fromString(playerId) != null) {
            Optional<PlayerProfile> playerProfileOpt = playerProfileRepository.findById(UUID.fromString(playerId));
            if (playerProfileOpt.isPresent()) {
                playerProfile = playerProfileOpt.get();
                for (Campaign runningCampaign : campaignService.getRunningCampaigns()) {
                    boolean assignCampaign = false;
                    if (runningCampaign.getMatchers() != null) {
                        if (runningCampaign.getMatchers().getHas() != null) {
                            // 1. Check HAS countries
                            if (runningCampaign.getMatchers().getHas().getCountry() != null
                                    && !runningCampaign.getMatchers().getHas().getCountry().isEmpty() && runningCampaign
                                            .getMatchers().getHas().getCountry().contains(playerProfile.getCountry())) {
                                // Country ok
                                assignCampaign = true;
                            }

                            // 2. Check HAS Items
                            if (runningCampaign.getMatchers().getHas().getItems() != null
                                    && !runningCampaign.getMatchers().getHas().getItems().isEmpty()
                                    && playerProfile.getInventory() != null) {

                                for (String itemName : playerProfile.getInventory().keySet()) {
                                    if (runningCampaign.getMatchers().getHas().getItems().contains(itemName)) {
                                        // At least one Item In Inventory is in HAS matcher --> Items ok
                                        assignCampaign = true;
                                    }
                                }
                            }
                        }
                        // 3. Check DOES NOT HAVE Items
                        if (runningCampaign.getMatchers().getDoesNotHave() != null
                                && runningCampaign.getMatchers().getDoesNotHave().getItems() != null
                                && !runningCampaign.getMatchers().getDoesNotHave().getItems().isEmpty()) {
                                    // Pensar mañana
                        }

                    }
                    if (assignCampaign)
                        playerProfile.getActiveCampaigns().add(runningCampaign);
                }

            }
        }

        return playerProfile;
    }
}