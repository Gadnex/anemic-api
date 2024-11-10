package net.binarypaper.anemic_api.backlog_item;

import java.util.UUID;

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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.binarypaper.anemic_api.product.Product;
import net.binarypaper.anemic_api.sprint.Sprint;

@Entity
@Data
public class BacklogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonView({ Views.List.class, Views.Read.class })
    @Schema(description = "The unique identifier of the backlog item", example = "1fe4fa4a-c693-48ed-bcd1-73da1891fce9")
    private UUID backlogItemId;

    @Version
    @JsonView({ Views.Read.class, Views.Update.class })
    @Schema(description = "The version of the backlog item used for concurrency purposes when updating", example = "0")
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;

    @Column(name = "product_id")
    @NotNull
    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class })
    @Schema(description = "The unique identifier of the product that the backlog item belongs to", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id", insertable = false, updatable = false)
    @JsonIgnore
    private Sprint sprint;

    @Column(name = "sprint_id")
    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class })
    @Schema(description = "The unique identifier of the sprint that the backlog item belongs to", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID sprintId;

    @NotBlank(message = "The backlog item name is required")
    @Size(min = 3, message = "Backlog item name must be at least {min} characters long")
    @JsonView({ Views.List.class, Views.Read.class, Views.Create.class, Views.Update.class })
    @Schema(description = "The display name of the backlog item", example = "Backlog Item 1")
    private String name;

    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class })
    @Schema(description = "The summary text of the backlog item", example = "Backlog Item 1 summary")
    private String summary;

    @Lob
    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class })
    @Schema(description = "The user story of the backlog item", example = "A story for Backlog Item 1")
    private String story;

    @Min(value = 1, message = "A user story may not have less than {min} story points")
    @Max(value = 21, message = "A user story may not have more than {max} story points")
    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class })
    @Schema(description = "The story points of the backlog item", example = "3")
    private Short storyPoints;

    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class })
    @Schema(description = "The type of backlog item")
    private BacklogItemType type;

    @JsonView({ Views.Read.class, Views.Create.class, Views.Update.class })
    @Schema(description = "The status of the backlog item belongs")
    private BacklogItemStatus status;

    public interface Views {
        public interface List {
        }

        public interface Read {
        }

        public interface Create {
        }

        public interface Update {
        }
    }
}
