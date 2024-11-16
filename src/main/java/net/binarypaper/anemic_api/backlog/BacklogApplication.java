package net.binarypaper.anemic_api.backlog;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.backlog.domain.*;
import org.hibernate.StaleObjectStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Retryable(retryFor = {StaleObjectStateException.class})
@AllArgsConstructor
public class BacklogApplication {

  private final BacklogItemRepository backlogItemRepository;

  @Transactional
  public ReadBacklogItemResponse createBacklogItem(
      @Valid CreateBacklogItemRequest createBacklogItemRequest) {
    BacklogItem backlogItem = new BacklogItem(createBacklogItemRequest);
    backlogItemRepository.save(backlogItem);
    return backlogItem.toBacklogItemReadResponse();
  }

  public List<ListBacklogItemResponse> getAllBacklogItems() {
    return backlogItemRepository.findBy(ListBacklogItemResponse.class);
  }

  public ReadBacklogItemResponse getBacklogItem(UUID backlogItemId) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    return backlogItem.toBacklogItemReadResponse();
  }

  @Transactional
  public void deleteBacklogItem(UUID backlogItemId) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    backlogItemRepository.delete(backlogItem);
  }

  @Transactional
  public ReadBacklogItemResponse commitToSprint(
      UUID backlogItemId, @Valid CommitSprintRequest commitSprintRequest) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    backlogItem.commitToSprint(commitSprintRequest);
    backlogItemRepository.save(backlogItem);
    return backlogItem.toBacklogItemReadResponse();
  }

  @Transactional
  public ReadBacklogItemResponse changeProgressStatus(
      UUID backlogItemId, @Valid ProgressStatusChangeRequest progressStatusChangeRequest) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    backlogItem.changeProgressStatus(progressStatusChangeRequest);
    backlogItemRepository.save(backlogItem);
    return backlogItem.toBacklogItemReadResponse();
  }

  @Transactional
  public ReadBacklogItemResponse addComment(
      UUID backlogItemId, @Valid AddCommentRequest addCommentRequest) {
    BacklogItem backlogItem = getBacklogItemById(backlogItemId);
    backlogItem.addComment(addCommentRequest);
    backlogItemRepository.save(backlogItem);
    return backlogItem.toBacklogItemReadResponse();
  }

  private BacklogItem getBacklogItemById(UUID backlogItemId) {
    return backlogItemRepository
        .findById(backlogItemId)
        .orElseThrow(BacklogItemNotFoundException::new);
  }
}
