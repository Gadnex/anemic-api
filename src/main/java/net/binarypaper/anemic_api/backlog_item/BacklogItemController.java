package net.binarypaper.anemic_api.backlog_item;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.product.Product;
import net.binarypaper.anemic_api.product.ProductRepository;
import net.binarypaper.anemic_api.sprint.Sprint;
import net.binarypaper.anemic_api.sprint.SprintRepository;

@RestController
@AllArgsConstructor
public class BacklogItemController implements BacklogItemAPI {

    private final BacklogItemRepository backlogItemRepository;

    private final ProductRepository productRepository;

    private final SprintRepository sprintRepository;

    @Override
    public BacklogItem createBacklogItem(@Valid BacklogItem backlogItem) {
        Product product = productRepository.findById(backlogItem.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product-id"));
        backlogItem.setProduct(product);
        if (backlogItem.getSprintId() != null) {
            Sprint sprint = sprintRepository.findById(backlogItem.getSprintId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sprint-id"));
            backlogItem.setSprint(sprint);
        }
        return backlogItemRepository.save(backlogItem);
    }

    @Override
    public List<BacklogItem> getAllBacklogItems() {
        return backlogItemRepository.findAll();
    }

    @Override
    public BacklogItem getBacklogItem(UUID backlogItemId) {
        return backlogItemRepository.findById(backlogItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public BacklogItem updateBacklogItem(UUID backlogItemId, @Valid BacklogItem backlogItem) {
        BacklogItem backlogItemFromDB = getBacklogItem(backlogItemId);
        if (!backlogItemFromDB.getVersion().equals(backlogItem.getVersion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The backlog item version is out of date. Please get the backlog item details again before updating.");
        }
        if (!backlogItem.getProductId().equals(backlogItemFromDB.getProductId())) {
            Product product = productRepository.findById(backlogItem.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product-id"));
            backlogItem.setProduct(product);
        }
        if (backlogItem.getSprintId() != null && !backlogItem.getSprintId().equals(backlogItemFromDB.getSprintId())) {
            Sprint sprint = sprintRepository.findById(backlogItem.getSprintId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sprint-id"));
            backlogItem.setSprint(sprint);
        }
        backlogItem.setBacklogItemId(backlogItemId);
        return backlogItemRepository.save(backlogItem);
    }

    @Override
    public void deleteBacklogItem(UUID backlogItemId) {
        BacklogItem backlogItem = getBacklogItem(backlogItemId);
        backlogItemRepository.delete(backlogItem);
    }

}
