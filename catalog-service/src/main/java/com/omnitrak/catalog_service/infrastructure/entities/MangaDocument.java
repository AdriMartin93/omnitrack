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
public class MangaDocument extends MediaDocument {

    public static final String FIELD_AUTHORS = "authors";

    private Integer totalVolumes;
    private Integer totalChapters;
    private List<String> authors;
    private String serialization;
    private String demographic;
}
