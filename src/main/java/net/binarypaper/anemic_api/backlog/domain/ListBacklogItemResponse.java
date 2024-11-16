package net.binarypaper.anemic_api.backlog.domain;

import java.util.UUID;

public record ListBacklogItemResponse(UUID backlogItemId, String name) {}
