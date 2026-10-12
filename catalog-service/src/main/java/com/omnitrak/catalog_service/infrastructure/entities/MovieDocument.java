package com.omnitrak.catalog_service.infrastructure.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MovieDocument extends MediaDocument {

    private Integer durationMinutes;
    private List<String> directors;
    private List<String> productionCompanies;
}
