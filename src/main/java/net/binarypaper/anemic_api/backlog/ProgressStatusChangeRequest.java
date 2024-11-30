package net.binarypaper.anemic_api.backlog;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import net.binarypaper.anemic_api.backlog.domain.BacklogItemStatus;
import org.springframework.hateoas.InputType;

public record ProgressStatusChangeRequest(
    @NotNull @JsonProperty("new-status") @InputType("text") BacklogItemStatus newStatus) {}
