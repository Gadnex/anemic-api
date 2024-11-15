package net.binarypaper.anemic_api.backlog_item;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.backlog_item.domain.BacklogItem;
import net.binarypaper.anemic_api.backlog_item.domain.BacklogItemRepository;
import net.binarypaper.anemic_api.product.ProductId;
import net.binarypaper.anemic_api.product.ProductService;
import net.binarypaper.anemic_api.sprint.SprintId;
import net.binarypaper.anemic_api.sprint.SprintService;
import org.hibernate.StaleObjectStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Retryable(retryFor = {StaleObjectStateException.class})
@AllArgsConstructor
public class BacklogItemService {

    private final BacklogItemRepository backlogItemRepository;

    private final ProductService productService;

    private final SprintService sprintService;


    @Transactional
    public BacklogItemReadResponse createBacklogItem(@Valid BacklogItemCreateRequest backlogItemCreateRequest) {
        productService.getProduct(new ProductId(backlogItemCreateRequest.productId()));
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
    public BacklogItemReadResponse commitToSprint(@Valid BacklogItemId backlogItemId, @Valid SprintId sprintId) {
        sprintService.getSprint(sprintId);
        BacklogItem backlogItem = getBacklogItemById(backlogItemId);
        backlogItem.commitToSprint(sprintId);
        backlogItemRepository.save(backlogItem);
        return backlogItem.toBacklogItemReadResponse();
    }

    @Transactional
    public BacklogItemReadResponse changeProgressStatus(@Valid BacklogItemId backlogItemId, @Valid ProgressStatusChangeRequest progressStatusChangeRequest) {
        BacklogItem backlogItem = getBacklogItemById(backlogItemId);
        backlogItem.changeProgressStatus(progressStatusChangeRequest);
        backlogItemRepository.save(backlogItem);
        return backlogItem.toBacklogItemReadResponse();
    }

    @Transactional
    public BacklogItemReadResponse addComment(@Valid BacklogItemId backlogItemId, @Valid AddCommentRequest addCommentRequest) {
        BacklogItem backlogItem = getBacklogItemById(backlogItemId);
        backlogItem.addComment(addCommentRequest);
        backlogItemRepository.save(backlogItem);
        return backlogItem.toBacklogItemReadResponse();
    }

    private BacklogItem getBacklogItemById(@Valid BacklogItemId backlogItemId) {
        return backlogItemRepository.findById(backlogItemId.backlogItemId())
                .orElseThrow(BacklogItemNotFoundException::new);
    }
}
