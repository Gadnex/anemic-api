package net.binarypaper.anemic_api.sprint.infrastructure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.product.ProductNotFoundException;
import net.binarypaper.anemic_api.sprint.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "sprint", produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = {"${application.cors.origins}"})
@Tag(name = "Sprint API", description = "Manage sprints")
@AllArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    @PostMapping
    @Operation(summary = "Create new sprint", description = """
            <b>As a</b> scrum master<br>
            <b>I want to</b> create a new sprint<br>
            <b>so that</b> my team can work on the sprint.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sprint created"),
            @ApiResponse(responseCode = "400", description = "Invalid sprint details", content = @Content)
    })
    public SprintReadResponse createSprint(@RequestBody @Valid SprintCreateRequest sprintCreateRequest) {
        try {
            return sprintService.createSprint(sprintCreateRequest);
        } catch (ProductNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product-id");
        }
    }

    @GetMapping
    @Operation(summary = "Get all sprints", description = """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view a list of all sprints<br>
            <b>so that</b> I can select the sprint I am working on.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of sprints returned")
    })
    public List<SprintListResponse> getAllSprints() {
        return sprintService.getAllSprints();
    }

    @GetMapping("{sprint-id}")
    @Operation(summary = "Get sprint details", description = """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view the details of a specific sprint<br>
            <b>so that</b> I can work on the sprint.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sprint returned"),
            @ApiResponse(responseCode = "404", description = "Sprint not found", content = @Content)
    })
    public SprintReadResponse getSprint(@PathVariable(name = "sprint-id") UUID sprintId) {
        return sprintService.getSprint(new SprintId(sprintId));
    }

    @PutMapping("{sprint-id}/plan")
    @Operation(summary = "Plan a existing sprint", description = """
            <b>As a</b> scrum master<br>
            <b>I want to</b> plan the start and end date of a sprint<br>
            <b>so that</b> my team can work on the sprint during this period.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sprint planned"),
            @ApiResponse(responseCode = "400", description = "Invalid sprint planning dates", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sprint not found", content = @Content)
    })
    public SprintReadResponse planSprint(@PathVariable(name = "sprint-id") UUID sprintId,
                                         @RequestBody @Valid PlanSprintRequest planSprintRequest) {
        return sprintService.planSprint(new SprintId(sprintId), planSprintRequest);
    }

    @DeleteMapping("{sprint-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete sprint", description = """
            <b>As a</b> scrum master<br>
            <b>I want to</b> delete a sprint<br>
            <b>so that</b> so that teams can no longer work on the sprint.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sprint deleted"),
            @ApiResponse(responseCode = "404", description = "Sprint not found", content = @Content)
    })
    void deleteSprint(@PathVariable(name = "sprint-id") UUID sprintId) {
        sprintService.deleteSprint(new SprintId(sprintId));
    }
}
