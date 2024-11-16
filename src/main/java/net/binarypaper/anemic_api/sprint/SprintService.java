package net.binarypaper.anemic_api.sprint;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.sprint.domain.Sprint;
import net.binarypaper.anemic_api.sprint.domain.SprintRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Retryable(retryFor = {StaleObjectStateException.class})
@AllArgsConstructor
public class SprintService {

  private final SprintRepository sprintRepository;

  @Transactional
  public SprintReadResponse createSprint(@Valid SprintCreateRequest sprintCreateRequest) {
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
  public SprintReadResponse planSprint(
      SprintId sprintId, @Valid PlanSprintRequest planSprintRequest) {
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
    return sprintRepository.findById(sprintId.sprintId()).orElseThrow(SprintNotFoundException::new);
  }
}
