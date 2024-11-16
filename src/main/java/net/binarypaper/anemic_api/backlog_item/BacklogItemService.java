package net.binarypaper.anemic_api.backlog_item;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.backlog_item.domain.BacklogItem;
import net.binarypaper.anemic_api.backlog_item.domain.BacklogItemRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Retryable(retryFor = {StaleObjectStateException.class})
@AllArgsConstructor
public class BacklogItemService {

  private final BacklogItemRepository backlogItemRepository;

  @Transactional
  public BacklogItemReadResponse createBacklogItem(
      @Valid BacklogItemCreateRequest backlogItemCreateRequest) {
    BacklogItem backlogItem = new BacklogItem(backlogItemCreateRequest);
    backlogItemRepository.save(backlogItem);
    return backlogItem.toBacklogItemReadResponse();
  }

  public List<BacklogItemListResponse> getAllBacklogItems() {
    return backlogItemRepository.findBy(BacklogItemListResponse.class);
  }

  public BacklogItemReadResponse getBacklogItem(@Valid BacklogItemId backlogItemId) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    return backlogItem.toBacklogItemReadResponse();
  }

  @Transactional
  public void deleteBacklogItem(@Valid BacklogItemId backlogItemId) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    backlogItemRepository.delete(backlogItem);
  }

  @Transactional
  public BacklogItemReadResponse commitToSprint(
      @Valid BacklogItemId backlogItemId, @Valid CommitSprintRequest commitSprintRequest) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    backlogItem.commitToSprint(commitSprintRequest);
    backlogItemRepository.save(backlogItem);
    return backlogItem.toBacklogItemReadResponse();
  }

  @Transactional
  public BacklogItemReadResponse changeProgressStatus(
      @Valid BacklogItemId backlogItemId,
      @Valid ProgressStatusChangeRequest progressStatusChangeRequest) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    backlogItem.changeProgressStatus(progressStatusChangeRequest);
    backlogItemRepository.save(backlogItem);
    return backlogItem.toBacklogItemReadResponse();
  }

  @Transactional
  public BacklogItemReadResponse addComment(
      @Valid BacklogItemId backlogItemId, @Valid AddCommentRequest addCommentRequest) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    backlogItem.addComment(addCommentRequest);
    backlogItemRepository.save(backlogItem);
    return backlogItem.toBacklogItemReadResponse();
  }

  private BacklogItem getBacklogItemById(@Valid BacklogItemId backlogItemId) {
    return backlogItemRepository
        .findById(backlogItemId.backlogItemId())
        .orElseThrow(BacklogItemNotFoundException::new);
  }
}
