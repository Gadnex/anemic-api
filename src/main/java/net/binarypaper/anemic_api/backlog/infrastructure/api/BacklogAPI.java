package net.binarypaper.anemic_api.backlog.infrastructure.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.backlog.BacklogApplication;
import net.binarypaper.anemic_api.backlog.domain.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping(path = "backlog-items")
@CrossOrigin(
    origins = {"${application.cors.origins}"},
    exposedHeaders = HttpHeaders.LOCATION)
@Tag(name = "Backlog API", description = "Manage the product backlog")
public class BacklogAPI {

  private final BacklogApplication backlogApplication;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
  @Operation(
      summary = "Create new backlog item",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> create a new backlog item<br>
            <b>so that</b> my team can work on the backlog item.
            """)
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Backlog item created",
        headers = {
          @Header(
              name = HttpHeaders.LOCATION,
              description = "The URL of the new backlog item",
              required = true)
        }),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid backlog item details",
        content = @Content)
  })
  public ResponseEntity<EntityModel<ReadBacklogItemResponse>> createBacklogItem(
      @RequestBody @Valid CreateBacklogItemRequest createBacklogItemRequest) {
    ReadBacklogItemResponse readBacklogItemResponse =
        backlogApplication.createBacklogItem(createBacklogItemRequest);
    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(readBacklogItemResponse.backlogItemId())
                .toUri())
        .body(EntityModel.of(readBacklogItemResponse));
  }

  @GetMapping
  @Operation(
      summary = "Get all backlog items",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view a list of all backlog items<br>
            <b>so that</b> I can select the backlog item I am working on.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "List of backlog items returned")
  })
  public CollectionModel<EntityModel<ListBacklogItemResponse>> getAllBacklogItems() {
    List<ListBacklogItemResponse> allBacklogItems = backlogApplication.getAllBacklogItems();
    return CollectionModel.of(
            allBacklogItems.stream().map(EntityModel::of).collect(Collectors.toList()))
        .add(
            linkTo(methodOn(BacklogAPI.class).getAllBacklogItems())
                .withSelfRel()
                .andAffordance(afford(methodOn(BacklogAPI.class).createBacklogItem(null)))
                .withRel("create-backlog-item")
                .withTitle("Create backlog item")
                .withName("default"));
  }

  @GetMapping("{backlog-item-id}")
  @Operation(
      summary = "Get backlog item details",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view the details of a specific backlog item<br>
            <b>so that</b> I can work on the backlog item.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Backlog item returned"),
    @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
  })
  public ResponseEntity<EntityModel<ReadBacklogItemResponse>> getBacklogItem(
      @PathVariable(name = "backlog-item-id") UUID backlogItemId) {
    ReadBacklogItemResponse readBacklogItemResponse =
        backlogApplication.getBacklogItem(backlogItemId);
    return ResponseEntity.ok(EntityModel.of(readBacklogItemResponse));
  }

  @DeleteMapping("{backlog-item-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Delete backlog item",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> delete a backlog item<br>
            <b>so that</b> so that teams can no longer work on the backlog item.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Backlog item deleted"),
    @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
  })
  public void deleteBacklogItem(@PathVariable(name = "backlog-item-id") UUID backlogItemId) {
    backlogApplication.deleteBacklogItem(backlogItemId);
  }

  @PutMapping("{backlog-item-id}/sprint")
  @Operation(
      summary = "Commit backlog item to a sprint",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> commit a backlog item to a sprint<br>
            <b>so that</b> I can work on the backlog item in the sprint.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Backlog item committed to sprint"),
    @ApiResponse(
        responseCode = "400",
        description = "Backlog item cannot be committed to sprint",
        content = @Content),
    @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
  })
  public EntityModel<ReadBacklogItemResponse> commitToSprint(
      @PathVariable(name = "backlog-item-id") UUID backlogItemId,
      @RequestBody @Valid CommitSprintRequest commitSprintRequest) {
    ReadBacklogItemResponse readBacklogItemResponse =
        backlogApplication.commitToSprint(backlogItemId, commitSprintRequest);
    return EntityModel.of(readBacklogItemResponse);
  }

  @PutMapping("{backlog-item-id}/progress")
  @Operation(
      summary = "Change the progress status of a backlog item",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> change the progress status of a backlog item<br>
            <b>so that</b> other team members and stakeholders can see the status of the backlog item.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Backlog item progress status changed"),
    @ApiResponse(
        responseCode = "400",
        description = "Backlog item progress status could not be changed",
        content = @Content),
    @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
  })
  public EntityModel<ReadBacklogItemResponse> changeProgressStatus(
      @PathVariable(name = "backlog-item-id") UUID backlogItemId,
      @RequestBody @Valid ProgressStatusChangeRequest progressStatusChangeRequest) {
    ReadBacklogItemResponse readBacklogItemResponse =
        backlogApplication.changeProgressStatus(backlogItemId, progressStatusChangeRequest);
    return EntityModel.of(readBacklogItemResponse);
  }

  @PutMapping("{backlog-item-id}/comments")
  @Operation(
      summary = "Add a comment to a backlog item",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> add a comment to a backlog item<br>
            <b>so that</b> the team can discuss the backlog item.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Backlog item comment added"),
    @ApiResponse(
        responseCode = "400",
        description = "Backlog item  comment could not be added",
        content = @Content),
    @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
  })
  public EntityModel<ReadBacklogItemResponse> addComment(
      @PathVariable(name = "backlog-item-id") UUID backlogItemId,
      @RequestBody @Valid AddCommentRequest addCommentRequest) {
    ReadBacklogItemResponse readBacklogItemResponse =
        backlogApplication.addComment(backlogItemId, addCommentRequest);
    return EntityModel.of(readBacklogItemResponse);
  }
}
