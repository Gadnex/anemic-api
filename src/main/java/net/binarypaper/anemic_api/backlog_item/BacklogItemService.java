package net.binarypaper.anemic_api.backlog_item;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.backlog_item.domain.BacklogItem;
import net.binarypaper.anemic_api.backlog_item.domain.BacklogItemRepository;
import net.binarypaper.anemic_api.product.ProductId;
import net.binarypaper.anemic_api.product.ProductService;
import net.binarypaper.anemic_api.sprint.SprintId;
import net.binarypaper.anemic_api.sprint.SprintService;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Retryable(noRetryFor = {IllegalArgumentException.class, IllegalStateException.class, ResponseStatusException.class})
@AllArgsConstructor
public class BacklogItemService {

    private final BacklogItemRepository backlogItemRepository;

    private final ProductService productService;

    private final SprintService sprintService;


    @Transactional
    public BacklogItemReadResponse createBacklogItem(@Valid BacklogItemCreateRequest backlogItemCreateRequest) {
        try {
            productService.getProduct(new ProductId(backlogItemCreateRequest.productId()));
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
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
        BacklogItem backlogItem = getBacklogItemById(backlogItemId);
        try {
            sprintService.getSprint(sprintId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
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
