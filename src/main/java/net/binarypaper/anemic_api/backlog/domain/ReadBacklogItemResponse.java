package net.binarypaper.anemic_api.backlog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;

public record ReadBacklogItemResponse(
    @JsonIgnore UUID backlogItemId,
    @JsonIgnore UUID productId,
    @JsonIgnore UUID sprintId,
    String name,
    String summary,
    String story,
    @JsonProperty("story-points") Short storyPoints,
    BacklogItemType type,
    BacklogItemStatus status,
    List<String> comment) {}
