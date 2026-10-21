package com.omnitrak.catalog_service.infrastructure.adapters.external.jikan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

public class JikanCommonDtos {

    @Data
    public static class SingleResponse<T> {  // para los endpoints que devuelven un {"data": {.......}}
        private T data;
    }

    @Data
    public static class ListResponse<T> {   // para los que devuelven lista paginada {"data": [.......]}
        private List<T> data;
    }

    @Data
    public static class JikanImagesDto {
        private JikanImageFormatsDto jpg;
        private JikanImageFormatsDto webp;
    }

    @Data
    public static class JikanImageFormatsDto {
        @JsonProperty("image_url")
                private String imageUrl;

        @JsonProperty("large_image_url")
                private String largeImageUrl;
    }

    @Data
    public static class JikanNamedDto {
        @JsonProperty("mal_id")
        private long malId;
        private String name;
    }

    @Data
    public static  class JikanDatesDto {
        private String from;
        private String to;
    }

    @Data
    public static class JikanBroadcastDto{
        private String day;
    }


}
