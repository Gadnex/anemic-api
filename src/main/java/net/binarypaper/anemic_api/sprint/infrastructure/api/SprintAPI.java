package net.binarypaper.anemic_api.sprint.infrastructure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.sprint.SprintApplication;
import net.binarypaper.anemic_api.sprint.domain.CreateSprintRequest;
import net.binarypaper.anemic_api.sprint.domain.ListSprintResponse;
import net.binarypaper.anemic_api.sprint.domain.PlanSprintRequest;
import net.binarypaper.anemic_api.sprint.domain.ReadSprintResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(
    path = "sprint",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = {"${application.cors.origins}"})
@Tag(name = "Sprint API", description = "Manage sprints")
@AllArgsConstructor
public class SprintAPI {

  private final SprintApplication sprintApplication;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
  @Operation(
      summary = "Create new sprint",
      description =
          """
            <b>As a</b> scrum master<br>
            <b>I want to</b> create a new sprint<br>
            <b>so that</b> my team can work on the sprint.
            """)
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Sprint created",
        headers = {
          @Header(
              name = HttpHeaders.LOCATION,
              description = "The URL of the new sprint",
              required = true)
        }),
    @ApiResponse(responseCode = "400", description = "Invalid sprint details", content = @Content)
  })
  public ResponseEntity<ReadSprintResponse> createSprint(
      @RequestBody @Valid CreateSprintRequest createSprintRequest) {
    ReadSprintResponse readSprintResponse = sprintApplication.createSprint(createSprintRequest);
    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(readSprintResponse.sprintId())
                .toUri())
        .body(readSprintResponse);
  }

  @GetMapping
  @Operation(
      summary = "Get all sprints",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view a list of all sprints<br>
            <b>so that</b> I can select the sprint I am working on.
            """)
  @ApiResponses({@ApiResponse(responseCode = "200", description = "List of sprints returned")})
  public List<ListSprintResponse> getAllSprints() {
    return sprintApplication.getAllSprints();
  }

  @GetMapping("{sprint-id}")
  @Operation(
      summary = "Get sprint details",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view the details of a specific sprint<br>
            <b>so that</b> I can work on the sprint.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Sprint returned"),
    @ApiResponse(responseCode = "404", description = "Sprint not found", content = @Content)
  })
  public ReadSprintResponse getSprint(@PathVariable(name = "sprint-id") UUID sprintId) {
    return sprintApplication.getSprint(sprintId);
  }

  @PutMapping("{sprint-id}/plan")
  @Operation(
      summary = "Plan a existing sprint",
      description =
          """
            <b>As a</b> scrum master<br>
            <b>I want to</b> plan the start and end date of a sprint<br>
            <b>so that</b> my team can work on the sprint during this period.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Sprint planned"),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid sprint planning dates",
        content = @Content),
    @ApiResponse(responseCode = "404", description = "Sprint not found", content = @Content)
  })
  public ReadSprintResponse planSprint(
      @PathVariable(name = "sprint-id") UUID sprintId,
      @RequestBody @Valid PlanSprintRequest planSprintRequest) {
    return sprintApplication.planSprint(sprintId, planSprintRequest);
  }

  @DeleteMapping("{sprint-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Delete sprint",
      description =
          """
            <b>As a</b> scrum master<br>
            <b>I want to</b> delete a sprint<br>
            <b>so that</b> so that teams can no longer work on the sprint.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Sprint deleted"),
    @ApiResponse(responseCode = "404", description = "Sprint not found", content = @Content)
  })
  void deleteSprint(@PathVariable(name = "sprint-id") UUID sprintId) {
    sprintApplication.deleteSprint(sprintId);
  }
}
