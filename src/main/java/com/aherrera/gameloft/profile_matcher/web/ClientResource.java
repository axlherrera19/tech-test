package com.aherrera.gameloft.profile_matcher.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aherrera.gameloft.profile_matcher.domain.PlayerProfile;
import com.aherrera.gameloft.profile_matcher.service.PlayerProfileService;

@RestController
@RequestMapping("/api/client")
public class ClientResource {

    @Autowired
    private PlayerProfileService playerProfileService;

    @GetMapping("/get_client_config/{player_id}")
    public ResponseEntity<PlayerProfile> getClientConfig(@PathVariable(name = "player_id", required = true) String playerId) {
        PlayerProfile result = playerProfileService.getClientConfigById(playerId);
        if(result != null)
            return ResponseEntity.ok(result);
        else
            return ResponseEntity.notFound().build();  
    }
}
