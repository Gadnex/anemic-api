package net.binarypaper.anemic_api.backlog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.binarypaper.anemic_api.backlog.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class BacklogItem {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID backlogItemId;

  @Version private Integer version;

  @NotNull private UUID productId;

  private UUID sprintId;

  @NotNull
  @Size(min = 3, max = 100)
  private String name;

  @Size(min = 3, max = 255)
  private String summary;

  @Lob private String story;

  @Min(value = 1)
  @Max(value = 21)
  private Short storyPoints;

  private BacklogItemType type;

  private BacklogItemStatus status;

  @ElementCollection
  @CollectionTable(name = "comment", joinColumns = @JoinColumn(name = "backlog_item_id"))
  @Column(name = "comment")
  private List<@NotNull @Size(min = 1, max = 1024) String> comments = new ArrayList<>();

  public BacklogItem(CreateBacklogItemRequest createBacklogItemRequest) {
    productId = createBacklogItemRequest.productId();
    name = createBacklogItemRequest.name();
    summary = createBacklogItemRequest.summary();
    status = BacklogItemStatus.BACKLOG;
  }

  public void commitToSprint(CommitSprintRequest commitSprintRequest) {
    if (sprintId != null) {
      throw new IllegalArgumentException("Sprint has already been committed");
    }
    sprintId = commitSprintRequest.sprintId();
    status = BacklogItemStatus.COMMITTED;
  }

  public void changeProgressStatus(ProgressStatusChangeRequest progressStatusChangeRequest) {
    if (sprintId == null) {
      throw new IllegalStateException("Backlog item is not assigned to a sprint");
    }
    status = progressStatusChangeRequest.newStatus();
  }

  public ReadBacklogItemResponse toBacklogItemReadResponse() {
    return new ReadBacklogItemResponse(
        backlogItemId,
        productId,
        sprintId,
        name,
        summary,
        story,
        storyPoints,
        type,
        status,
        comments);
  }

  public void addComment(AddCommentRequest addCommentRequest) {
    comments.add(addCommentRequest.comment());
  }
}
