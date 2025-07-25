package com.aherrera.gameloft.profile_matcher.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aherrera.gameloft.profile_matcher.domain.Campaign;
import com.aherrera.gameloft.profile_matcher.repository.CampaingRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of ICampaignService that retrieves all running Campaigns from
 * DB by using the JPS Repository.
 * Used by Campaign API
 */
@Service("campaignDBService")
@Slf4j
public class CampaignService implements ICampaignService {

    @Autowired
    private CampaingRepository campaingRepository;

    @Override
    public List<Campaign> getRunningCampaigns() {
        List<Campaign> resultList = new ArrayList<>();
        List<Campaign> tmp = (List<Campaign>) campaingRepository.findAll();
        if(tmp != null)
            resultList = tmp;
        
        return resultList;
    }
}