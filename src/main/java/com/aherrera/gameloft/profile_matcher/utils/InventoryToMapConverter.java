package com.aherrera.gameloft.profile_matcher.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class InventoryToMapConverter implements AttributeConverter<Map<String, Integer>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Integer> inventoryMap) {
        if (inventoryMap == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(inventoryMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Map to JSON string", e);
        }
    }

    @Override
    public Map<String, Integer> convertToEntityAttribute(String inventoryJson) {
        if (inventoryJson == null || inventoryJson.trim().isEmpty()) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(inventoryJson, new TypeReference<Map<String, Integer>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON string to Map", e);
        }
    }
}