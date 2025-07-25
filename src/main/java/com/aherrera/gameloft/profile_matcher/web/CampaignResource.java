package com.aherrera.gameloft.profile_matcher.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aherrera.gameloft.profile_matcher.domain.Campaign;
import com.aherrera.gameloft.profile_matcher.service.ICampaignService;

/**
 * Campaign API
 * I decided to create it anyway instead of mocking it.
 * Gets Campaign data from DB by using specific ICampaignService implementation
 */
@RestController
@RequestMapping("/api/campaign")
public class CampaignResource {

    @Autowired
    @Qualifier("campaignDBService")
    private ICampaignService campaignService;

    @GetMapping("/get_running_campaigns")
    public ResponseEntity<List<Campaign>> getCurrentCampaign() {
        return ResponseEntity.ok(campaignService.getRunningCampaigns());
    }
}
