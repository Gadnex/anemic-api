package net.binarypaper.anemic_api.backlog;

import java.util.UUID;

public record BacklogItemListResponse(UUID backlogItemId, String name) {}
