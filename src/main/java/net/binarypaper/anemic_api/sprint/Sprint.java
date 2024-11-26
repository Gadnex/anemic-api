package net.binarypaper.anemic_api.sprint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
import net.binarypaper.anemic_api.product.Product;

@Entity
@Data
public class Sprint {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonView({Views.List.class, Views.Read.class})
  @Schema(
      description = "The unique identifier of the sprint",
      example = "8f82a93a-de1c-4c3d-b092-079092c6fb07")
  private UUID sprintId;

  @Version
  @JsonView({Views.Read.class, Views.Update.class})
  @Schema(
      description = "The version of the sprint used for concurrency purposes when updating",
      example = "0")
  private Integer version;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  @JsonIgnore
  private Product product;

  @Column(name = "product_id")
  @NotNull
  @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
  @Schema(
      description = "The unique identifier of the product that the sprint belongs to",
      example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
  private UUID productId;

  @JsonView({Views.List.class, Views.Read.class, Views.Create.class, Views.Update.class})
  @Schema(description = "The name of the sprint", example = "Sprint 1")
  private String name;

  @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
  @Schema(description = "The start date of the sprint")
  private LocalDate startDate;

  @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
  @Schema(description = "The end date of the sprint")
  private LocalDate endDate;

  public interface Views {
    public interface List {}

    public interface Read {}

    public interface Create {}

    public interface Update {}
  }
}
