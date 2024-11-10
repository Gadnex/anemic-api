package net.binarypaper.anemic_api.backlog_item;

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

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping(path = "backlog-items", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = { "${application.cors.origins}" })
@Tag(name = "Backlog Item API", description = "Manage backlog items")
public interface BacklogItemAPI {

    @PostMapping
    @JsonView(BacklogItem.Views.Read.class)
    @Operation(summary = "Create new backlog item", description = """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> create a new backlog item<br>
            <b>so that</b> my team can work on the backlog item.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Backlog item created"),
            @ApiResponse(responseCode = "400", description = "Invalid backlog item details", content = @Content)
    })
    BacklogItem createBacklogItem(
            @RequestBody @JsonView(BacklogItem.Views.Create.class) @Valid BacklogItem backlogItem);

    @GetMapping
    @JsonView(BacklogItem.Views.List.class)
    @Operation(summary = "Get all backlog item", description = """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view a list of all backlog item<br>
            <b>so that</b> I can select the backlog item I am working on.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of backlog items returned")
    })
    List<BacklogItem> getAllBacklogItems();

    @GetMapping("{backlog-item-id}")
    @JsonView(BacklogItem.Views.Read.class)
    @Operation(summary = "Get backlog item details", description = """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view the details of a specific backlog item<br>
            <b>so that</b> I can work on the backlog item.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Backlog item returned"),
            @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
    })
    BacklogItem getBacklogItem(@PathVariable(name = "backlog-item-id") UUID backlogItemId);

    @PutMapping("{backlog-item-id}")
    @JsonView(BacklogItem.Views.Read.class)
    @Operation(summary = "Update an existing backlog item", description = """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> update an existing backlog item<br>
            <b>so that</b> my team can work on the backlog item.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Backlog item updated"),
            @ApiResponse(responseCode = "400", description = "Invalid backlog item details", content = @Content),
            @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
    })
    BacklogItem updateBacklogItem(@PathVariable(name = "backlog-item-id") UUID backlogItemId,
            @RequestBody @JsonView(BacklogItem.Views.Update.class) @Valid BacklogItem backlogItem);

    @DeleteMapping("{backlog-item-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete backlog item", description = """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> delete a backlog item<br>
            <b>so that</b> so that teams can no longer work on the backlog item.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Backlog item deleted"),
            @ApiResponse(responseCode = "404", description = "Backlog item not found", content = @Content)
    })
    void deleteBacklogItem(@PathVariable(name = "backlog-item-id") UUID backlogItemId);
}
