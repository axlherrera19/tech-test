package com.aherrera.gameloft.profile_matcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aherrera.gameloft.profile_matcher.domain.Campaign;


@Repository
public interface CampaingRepository extends CrudRepository<Campaign, Long> {
}
