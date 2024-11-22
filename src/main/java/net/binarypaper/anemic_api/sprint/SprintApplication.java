package net.binarypaper.anemic_api.sprint;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.sprint.domain.*;
import org.hibernate.StaleObjectStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Retryable(retryFor = {StaleObjectStateException.class})
@AllArgsConstructor
public class SprintApplication {

  private final SprintRepository sprintRepository;

  @Transactional
  public ReadSprintResponse createSprint(@Valid CreateSprintRequest createSprintRequest) {
    Sprint sprint = new Sprint(createSprintRequest);
    sprintRepository.save(sprint);
    return sprint.toSprintReadResponse();
  }

  public List<ListSprintResponse> getAllSprints() {
    return sprintRepository.findBy(ListSprintResponse.class);
  }

  public ReadSprintResponse getSprint(UUID sprintId) {
    Sprint sprint = getSprintById(sprintId);
    return sprint.toSprintReadResponse();
  }

  @Transactional
  public ReadSprintResponse planSprint(UUID sprintId, @Valid PlanSprintRequest planSprintRequest) {
    Sprint sprint = getSprintById(sprintId);
    sprint.planSprint(planSprintRequest);
    sprintRepository.save(sprint);
    return sprint.toSprintReadResponse();
  }

  @Transactional
  public void deleteSprint(UUID sprintId) {
    Sprint sprint = getSprintById(sprintId);
    sprintRepository.delete(sprint);
  }

  private Sprint getSprintById(UUID sprintId) {
    return sprintRepository.findById(sprintId).orElseThrow(SprintNotFoundException::new);
  }
}
