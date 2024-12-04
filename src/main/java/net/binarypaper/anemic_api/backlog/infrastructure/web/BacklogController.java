package net.binarypaper.anemic_api.backlog.infrastructure.web;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.binarypaper.anemic_api.backlog.BacklogApplication;
import net.binarypaper.anemic_api.backlog.BacklogItemNotFoundException;
import net.binarypaper.anemic_api.backlog.CommitSprintRequest;
import net.binarypaper.anemic_api.backlog.ReadBacklogItemResponse;
import net.binarypaper.anemic_api.product.ProductApplication;
import net.binarypaper.anemic_api.product.ReadProductResponse;
import net.binarypaper.anemic_api.sprint.SprintApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("backlog-items")
@AllArgsConstructor
@Slf4j
public class BacklogController {

  private final BacklogApplication backlogApplication;

  private final ProductApplication productApplication;

  private final SprintApplication sprintApplication;

  @GetMapping
  public String viewBacklogItems(
      @RequestHeader(name = "Hx-Request", required = false) String hxRequest, Model model) {
    model.addAttribute("backlogItems", backlogApplication.getAllBacklogItems());
    if (hxRequest != null) {
      return "backlog/backlog-items :: main";
    }
    return "backlog/backlog-items";
  }

  @GetMapping("create")
  public String viewCreateBacklogItem(
      @RequestHeader(name = "Hx-Request", required = false) String hxRequest, Model model) {
    if (hxRequest != null) {
      return "backlog/create-backlog-item :: create-backlog-item";
    }
    model.addAttribute("create", true);
    return viewBacklogItems(hxRequest, model);
  }

  @GetMapping("{id}")
  public String viewBacklogItem(
      @PathVariable UUID id,
      @RequestHeader(name = "Hx-Request", required = false) String hxRequest,
      Model model) {
    ReadBacklogItemResponse readBacklogItemResponse;
    try {
      readBacklogItemResponse = backlogApplication.getBacklogItem(id);
      model.addAttribute("backlogItem", readBacklogItemResponse);
    } catch (BacklogItemNotFoundException ex) {
      log.atWarn()
          .addKeyValue("backlogItemId", id.toString())
          .log("Tried to access invalid backlog item");
      model.addAttribute("message", "Not Found");
      return "error";
    }
    ReadProductResponse readProductResponse =
        productApplication.getProduct(readBacklogItemResponse.productId());
    model.addAttribute("product", readProductResponse);
    if (readBacklogItemResponse.sprintId() == null) {
      model.addAttribute("sprints", sprintApplication.getAllSprints());
    } else {
      model.addAttribute("sprint", sprintApplication.getSprint(readBacklogItemResponse.sprintId()));
    }
    if (hxRequest != null) {
      return "backlog/backlog-item :: main";
    }
    return "backlog/backlog-item";
  }

  @PutMapping("{id}/sprint")
  public String commitToSprint(
      @PathVariable UUID id, @ModelAttribute CommitSprintRequest commitSprintRequest, Model model) {
    ReadBacklogItemResponse readBacklogItemResponse;
    try {
      readBacklogItemResponse = backlogApplication.commitToSprint(id, commitSprintRequest);
    } catch (BacklogItemNotFoundException ex) {
      log.atError()
          .addKeyValue("backlogItemId", id.toString())
          .log("Tried to commit to sprint for invalid backlog item");
      model.addAttribute("message", "Not Found");
      return "error";
    }
    model.addAttribute("backlogItem", readBacklogItemResponse);
    model.addAttribute("sprint", sprintApplication.getSprint(readBacklogItemResponse.sprintId()));
    ReadProductResponse readProductResponse =
        productApplication.getProduct(readBacklogItemResponse.productId());
    model.addAttribute("product", readProductResponse);
    return "backlog/backlog-item :: main";
  }
}
