package com.aherrera.gameloft.profile_matcher.service;

import java.util.List;

import com.aherrera.gameloft.profile_matcher.domain.Campaign;

/**
 * This allows me to have several implementations of getRunningCampaigns
 */
public interface ICampaignService {

    /**
     * 
     * @return List of Running "Campaign" objects. If there aren't, it returns empty List
     */
    public List<Campaign> getRunningCampaigns();
}
