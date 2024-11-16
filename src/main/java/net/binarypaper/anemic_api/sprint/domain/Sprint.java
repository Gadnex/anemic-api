package net.binarypaper.anemic_api.sprint.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.binarypaper.anemic_api.sprint.PlanSprintRequest;
import net.binarypaper.anemic_api.sprint.SprintCreateRequest;
import net.binarypaper.anemic_api.sprint.SprintReadResponse;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class Sprint {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID sprintId;

  @Version private Integer version;

  @Column(name = "product_id")
  @NotNull
  private UUID productId;

  @NotNull
  @Size(min = 3, max = 100)
  private String name;

  @FutureOrPresent private LocalDate startDate;

  @Future private LocalDate endDate;

  public Sprint(SprintCreateRequest sprintCreateRequest) {
    productId = sprintCreateRequest.productId();
    name = sprintCreateRequest.name();
  }

  public SprintReadResponse toSprintReadResponse() {
    return new SprintReadResponse(sprintId, productId, name, startDate, endDate);
  }

  public void planSprint(PlanSprintRequest planSprintRequest) {
    startDate = planSprintRequest.startDate();
    endDate = planSprintRequest.endDate();
  }
}
