package com.aherrera.gameloft.profile_matcher.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        PlayerProfile result = null;
        if (playerId != null && UUID.fromString(playerId) != null) {
            Optional<PlayerProfile> playerProfileOpt = playerProfileRepository.findById(UUID.fromString(playerId));
            if (playerProfileOpt.isPresent()) {
                result = playerProfileOpt.get();
                campaignService.getRunningCampaigns().forEach(runningCampaign -> {
                    //El algoritmo aqui
                    runningCampaign.getMatchers().getHas().getCountry()
                });
            }
        }

        return result;
    }
}