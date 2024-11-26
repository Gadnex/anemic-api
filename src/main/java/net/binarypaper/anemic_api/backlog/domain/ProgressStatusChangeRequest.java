package net.binarypaper.anemic_api.backlog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.InputType;

public record ProgressStatusChangeRequest(
    @NotNull @JsonProperty("new-status") @InputType("text") BacklogItemStatus newStatus) {}
