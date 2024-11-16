package net.binarypaper.anemic_api.backlog;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BacklogItemId(@NotNull UUID backlogItemId) {}
