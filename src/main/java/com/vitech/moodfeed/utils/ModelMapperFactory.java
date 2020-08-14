package com.vitech.moodfeed.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperFactory {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    private ModelMapperFactory() {
    }

    static {
        MODEL_MAPPER.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static ModelMapper mapper() {
        return MODEL_MAPPER;
    }

}
