package net.binarypaper.anemic_api.backlog_item;

import java.util.List;
import java.util.UUID;
import net.binarypaper.anemic_api.backlog_item.domain.BacklogItemStatus;
import net.binarypaper.anemic_api.backlog_item.domain.BacklogItemType;

public record BacklogItemReadResponse(
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
