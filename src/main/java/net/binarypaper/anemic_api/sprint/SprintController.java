package net.binarypaper.anemic_api.sprint;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.product.Product;
import net.binarypaper.anemic_api.product.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class SprintController implements SprintAPI {

  private final SprintRepository sprintRepository;

  private final ProductRepository productRepository;

  @Override
  public Sprint createSprint(@Valid Sprint sprint) {
    Product product =
        productRepository
            .findById(sprint.getProductId())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product-id"));
    sprint.setProduct(product);
    return sprintRepository.save(sprint);
  }

  @Override
  public List<Sprint> getAllSprints() {
    return sprintRepository.findAll();
  }

  @Override
  public Sprint getSprint(UUID sprintId) {
    return sprintRepository
        .findById(sprintId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Override
  public Sprint updateSprint(UUID sprintId, @Valid Sprint sprint) {
    Sprint sprintFromDB = getSprint(sprintId);
    if (!sprintFromDB.getVersion().equals(sprint.getVersion())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "The sprint version is out of date. Please get the sprint details again before updating.");
    }
    if (!sprint.getProductId().equals(sprintFromDB.getProductId())) {
      Product product =
          productRepository
              .findById(sprint.getProductId())
              .orElseThrow(
                  () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product-id"));
      sprint.setProduct(product);
    }
    sprint.setSprintId(sprintId);
    return sprintRepository.save(sprint);
  }

  @Override
  public void deleteSprint(UUID sprintId) {
    Sprint sprint = getSprint(sprintId);
    sprintRepository.delete(sprint);
  }
}
