package net.binarypaper.anemic_api.backlog_item.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import net.binarypaper.anemic_api.backlog_item.AddCommentRequest;
import net.binarypaper.anemic_api.backlog_item.BacklogItemCreateRequest;
import net.binarypaper.anemic_api.backlog_item.BacklogItemReadResponse;
import net.binarypaper.anemic_api.backlog_item.ProgressStatusChangeRequest;
import net.binarypaper.anemic_api.product.domain.Product;
import net.binarypaper.anemic_api.sprint.domain.Sprint;
import net.binarypaper.anemic_api.sprint.SprintId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class BacklogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID backlogItemId;

    @Version
    private Integer version;

    @NotNull
    private UUID productId;

    private UUID sprintId;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @Size(min = 3, max = 255)
    private String summary;

    @Lob
    private String story;

    @Min(value = 1, message = "A user story may not have less than {min} story points")
    @Max(value = 21, message = "A user story may not have more than {max} story points")
    private Short storyPoints;

    private BacklogItemType type;

    private BacklogItemStatus status;

    @ElementCollection
    @CollectionTable(
            name = "comment",
            joinColumns = @JoinColumn(name = "backlog_item_id")
    )
    @Column(name = "comment")
    private List<@NotNull @Size(min = 1, max = 1024) String> comments = new ArrayList<>();

    public BacklogItem(BacklogItemCreateRequest backlogItemCreateRequest) {
        productId = backlogItemCreateRequest.productId();
        name = backlogItemCreateRequest.name();
        summary = backlogItemCreateRequest.summary();
        status = BacklogItemStatus.BACKLOG;
    }

    public void commitToSprint(SprintId aSprintId) {
        if (sprintId != null) {
            throw new IllegalArgumentException("Sprint has already been committed");
        }
        sprintId = aSprintId.sprintId();
        status = BacklogItemStatus.COMMITTED;
    }

    public void changeProgressStatus(ProgressStatusChangeRequest progressStatusChangeRequest) {
        if (sprintId == null) {
            throw new IllegalStateException("Backlog item is not assigned to a sprint");
        }
        status = progressStatusChangeRequest.newStatus();
    }

    public BacklogItemReadResponse toBacklogItemReadResponse() {
        return new BacklogItemReadResponse(backlogItemId, productId, sprintId, name, summary, story, storyPoints, type, status, comments);
    }

    public void addComment(AddCommentRequest addCommentRequest) {
        comments.add(addCommentRequest.comment());
    }
}
