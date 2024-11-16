package net.binarypaper.anemic_api.backlog.domain;

import jakarta.validation.constraints.NotNull;

public record ProgressStatusChangeRequest(@NotNull BacklogItemStatus newStatus) {}
