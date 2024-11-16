package net.binarypaper.anemic_api.backlog;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddCommentRequest(@NotNull @Size(min = 1, max = 1024) String comment) {}
