package net.binarypaper.anemic_api.backlog_item;

import jakarta.validation.constraints.NotNull;
import net.binarypaper.anemic_api.backlog_item.domain.BacklogItemStatus;

public record ProgressStatusChangeRequest(@NotNull BacklogItemStatus newStatus) {}
