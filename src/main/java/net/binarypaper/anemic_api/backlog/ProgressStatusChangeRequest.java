package net.binarypaper.anemic_api.backlog;

import jakarta.validation.constraints.NotNull;
import net.binarypaper.anemic_api.backlog.domain.BacklogItemStatus;

public record ProgressStatusChangeRequest(@NotNull BacklogItemStatus newStatus) {}
