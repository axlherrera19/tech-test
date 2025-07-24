package com.aherrera.gameloft.profile_matcher.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convert Strings representing List of strings separated by , into a List<String> object
 */
@Converter
public class StringToListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        if (stringList == null || stringList.isEmpty()) {
            return null;
        }
        return stringList.stream()
                         .collect(Collectors.joining(SPLIT_DELIMITER));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return List.of();
        }
    
        return Arrays.stream(dbData.split(SPLIT_DELIMITER))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.toList());
    }
}