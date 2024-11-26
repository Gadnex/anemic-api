package net.binarypaper.anemic_api.backlog.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.InputType;

public record AddCommentRequest(
    @NotNull @Size(min = 1, max = 1024) @InputType("textarea") String comment) {}
