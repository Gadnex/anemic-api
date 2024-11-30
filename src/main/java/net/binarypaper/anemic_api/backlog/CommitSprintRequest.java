package net.binarypaper.anemic_api.backlog;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.hateoas.InputType;

public record CommitSprintRequest(
    @NotNull @JsonProperty("sprint-id") @InputType("text") UUID sprintId) {}
