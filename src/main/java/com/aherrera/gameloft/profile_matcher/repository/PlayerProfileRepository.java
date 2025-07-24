package com.aherrera.gameloft.profile_matcher.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aherrera.gameloft.profile_matcher.domain.PlayerProfile;

@Repository
public interface PlayerProfileRepository extends CrudRepository<PlayerProfile, UUID> {
}
