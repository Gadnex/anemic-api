package net.binarypaper.anemic_api.backlog.infrastructure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.backlog.*;
import net.binarypaper.anemic_api.product.ProductNotFoundException;
import net.binarypaper.anemic_api.sprint.SprintNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(
    path = "backlog-items",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = {"${application.cors.origins}"})
@Tag(name = "Backlog API", description = "Manage the product backlog")
public class BacklogAPI {

  private final BacklogApplication backlogApplication;

  @PostMapping
  @Operation(
      summary = "Create new backlog item",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> create a new backlog item<br>
            <b>so that</b> my team can work on the backlog item.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Backlog item created"),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid backlog item details",
        content = @Content)
  })
  public BacklogItemReadResponse createBacklogItem(
      @RequestBody @Valid BacklogItemCreateRequest backlogItemCreateRequest) {
    try {
      return backlogApplication.createBacklogItem(backlogItemCreateRequest);
    } catch (ProductNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product-id");
    }
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
  public List<BacklogItemListResponse> getAllBacklogItems() {
    return backlogApplication.getAllBacklogItems();
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
  public BacklogItemReadResponse getBacklogItem(
      @PathVariable(name = "backlog-item-id") UUID backlogItemId) {
    return backlogApplication.getBacklogItem(new BacklogItemId(backlogItemId));
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
    backlogApplication.deleteBacklogItem(new BacklogItemId(backlogItemId));
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
  public BacklogItemReadResponse commitToSprint(
      @PathVariable(name = "backlog-item-id") UUID backlogItemId,
      @RequestBody @Valid CommitSprintRequest commitSprintRequest) {
    try {
      return backlogApplication.commitToSprint(
          new BacklogItemId(backlogItemId), commitSprintRequest);
    } catch (SprintNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sprint-id");
    }
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
  public BacklogItemReadResponse changeProgressStatus(
      @PathVariable(name = "backlog-item-id") UUID backlogItemId,
      @RequestBody @Valid ProgressStatusChangeRequest progressStatusChangeRequest) {
    return backlogApplication.changeProgressStatus(
        new BacklogItemId(backlogItemId), progressStatusChangeRequest);
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
  public BacklogItemReadResponse addComment(
      @PathVariable(name = "backlog-item-id") UUID backlogItemId,
      @RequestBody @Valid AddCommentRequest addCommentRequest) {
    return backlogApplication.addComment(new BacklogItemId(backlogItemId), addCommentRequest);
  }
}
