package com.aherrera.gameloft.profile_matcher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aherrera.gameloft.profile_matcher.domain.Campaign;
import com.aherrera.gameloft.profile_matcher.repository.CampaingRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CampaignService {

    @Autowired
    private CampaingRepository campaingRepository;

    public List<Campaign> getRunningCampaigns() {
        return (List<Campaign>) campaingRepository.findAll();
    }
}