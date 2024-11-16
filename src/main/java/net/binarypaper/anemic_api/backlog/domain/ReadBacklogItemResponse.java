package net.binarypaper.anemic_api.backlog.domain;

import java.util.List;
import java.util.UUID;

public record ReadBacklogItemResponse(
    UUID backlogItemId,
    UUID productId,
    UUID sprintId,
    String name,
    String summary,
    String story,
    Short storyPoints,
    BacklogItemType type,
    BacklogItemStatus status,
    List<String> comment) {}
