package com.aherrera.gameloft.profile_matcher.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aherrera.gameloft.profile_matcher.domain.Campaign;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of ICampaignService that retrieves all running Campaigns from
 * an "external" by using the RestTemplate. URI of the service can be found at
 * the application configuration
 * Used by PlayerProfileService
 */
@Service("campaignHttpService")
@Slf4j
public class CampaignHttpService implements ICampaignService {

    @Value("${application.campaing-http-service.uri}")
    private String apiUri;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Campaign> getRunningCampaigns() {
        List<Campaign> resultList = new ArrayList<>();
        ResponseEntity<Campaign[]> response = restTemplate.getForEntity(
                apiUri + "/api/campaign/get_running_campaigns",
                Campaign[].class);
        Campaign[] campaignsArray = response != null && response.getBody() != null ? response.getBody() : null;
        if(campaignsArray != null)
            resultList = Arrays.asList(campaignsArray);

        return resultList;
    }
}