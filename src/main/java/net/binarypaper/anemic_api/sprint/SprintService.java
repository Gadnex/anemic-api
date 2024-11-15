package net.binarypaper.anemic_api.sprint;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.product.ProductId;
import net.binarypaper.anemic_api.product.ProductService;
import net.binarypaper.anemic_api.sprint.domain.Sprint;
import net.binarypaper.anemic_api.sprint.domain.SprintRepository;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Retryable(noRetryFor = {IllegalArgumentException.class, IllegalStateException.class, ResponseStatusException.class})
@AllArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;

    private final ProductService productService;

    @Transactional
    public SprintReadResponse createSprint(@Valid SprintCreateRequest sprintCreateRequest) {
        try {
            productService.getProduct(new ProductId(sprintCreateRequest.productId()));
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e.getMessage());
        }
        Sprint sprint = new Sprint(sprintCreateRequest);
        sprintRepository.save(sprint);
        return sprint.toSprintReadResponse();
    }

    public List<SprintListResponse> getAllSprints() {
        return sprintRepository.findBy(SprintListResponse.class);
    }

    public SprintReadResponse getSprint(SprintId sprintId) {
        Sprint sprint = getSprintById(sprintId);
        return sprint.toSprintReadResponse();
    }

    @Transactional
    public SprintReadResponse planSprint(SprintId sprintId, @Valid PlanSprintRequest planSprintRequest) {
        Sprint sprint = getSprintById(sprintId);
        sprint.planSprint(planSprintRequest);
        sprintRepository.save(sprint);
        return sprint.toSprintReadResponse();
    }

    @Transactional
    public void deleteSprint(SprintId sprintId) {
        Sprint sprint = getSprintById(sprintId);
        sprintRepository.delete(sprint);
    }

    private Sprint getSprintById(SprintId sprintId) {
        return sprintRepository.findById(sprintId.sprintId())
                .orElseThrow(SprintNotFoundException::new);
    }
}
