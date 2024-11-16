package net.binarypaper.anemic_api.backlog;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CommitSprintRequest(@NotNull UUID sprintId) {}
