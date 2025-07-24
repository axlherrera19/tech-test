package com.aherrera.gameloft.profile_matcher.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aherrera.gameloft.profile_matcher.domain.Campaign;
import com.aherrera.gameloft.profile_matcher.service.CampaignService;

@RestController
@RequestMapping("/api/campaign")
public class CampaignResource {

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/get_running_campaigns")
    public ResponseEntity<List<Campaign>> getCurrentCampaign() {
        return ResponseEntity.ok(campaignService.getRunningCampaigns());
    }
}
