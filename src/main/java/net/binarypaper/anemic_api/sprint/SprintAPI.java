package net.binarypaper.anemic_api.sprint;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(
    path = "sprint",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = {"${application.cors.origins}"})
@Tag(name = "Sprint API", description = "Manage sprints")
public interface SprintAPI {

  @PostMapping
  @JsonView(Sprint.Views.Read.class)
  @Operation(
      summary = "Create new sprint",
      description =
          """
            <b>As a</b> scrum master<br>
            <b>I want to</b> create a new sprint<br>
            <b>so that</b> my team can work on the sprint.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Sprint created"),
    @ApiResponse(responseCode = "400", description = "Invalid sprint details", content = @Content)
  })
  Sprint createSprint(@RequestBody @JsonView(Sprint.Views.Create.class) @Valid Sprint sprint);

  @GetMapping
  @JsonView(Sprint.Views.List.class)
  @Operation(
      summary = "Get all sprints",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view a list of all sprints<br>
            <b>so that</b> I can select the sprint I am working on.
            """)
  @ApiResponses({@ApiResponse(responseCode = "200", description = "List of sprints returned")})
  List<Sprint> getAllSprints();

  @GetMapping("{sprint-id}")
  @JsonView(Sprint.Views.Read.class)
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
  Sprint getSprint(@PathVariable(name = "sprint-id") UUID sprintId);

  @PutMapping("{sprint-id}")
  @JsonView(Sprint.Views.Read.class)
  @Operation(
      summary = "Update an existing sprint",
      description =
          """
            <b>As a</b> scrum master<br>
            <b>I want to</b> update an existing sprint<br>
            <b>so that</b> my team can work on the sprint.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Sprint updated"),
    @ApiResponse(responseCode = "400", description = "Invalid sprint details", content = @Content),
    @ApiResponse(responseCode = "404", description = "Sprint not found", content = @Content)
  })
  Sprint updateSprint(
      @PathVariable(name = "sprint-id") UUID sprintId,
      @RequestBody @JsonView(Sprint.Views.Update.class) @Valid Sprint sprint);

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
  void deleteSprint(@PathVariable(name = "sprint-id") UUID sprintId);
}
