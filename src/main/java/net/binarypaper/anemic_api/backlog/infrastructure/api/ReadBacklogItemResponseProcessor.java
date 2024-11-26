package net.binarypaper.anemic_api.backlog.infrastructure.api;

import static org.springframework.hateoas.server.mvc.BasicLinkBuilder.linkToCurrentMapping;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import net.binarypaper.anemic_api.backlog.domain.ReadBacklogItemResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class ReadBacklogItemResponseProcessor
    implements RepresentationModelProcessor<EntityModel<ReadBacklogItemResponse>> {

  @Override
  public EntityModel<ReadBacklogItemResponse> process(EntityModel<ReadBacklogItemResponse> model) {
    model.add(
        linkTo(methodOn(BacklogAPI.class).getBacklogItem(model.getContent().backlogItemId()))
            .withSelfRel());
    model.add(linkTo(methodOn(BacklogAPI.class).getAllBacklogItems()).withRel("all-backlog-items"));
    model.add(
        linkToCurrentMapping()
            .slash("products")
            .slash(model.getContent().productId())
            .withRel("product"));
    model.add(
        linkTo(
                methodOn(BacklogAPI.class)
                    .changeProgressStatus(model.getContent().backlogItemId(), null))
            .withRel("change-progress-status")
            .withTitle("Change progress status")
            .withName("default"));
    if (model.getContent().sprintId() == null) {
      model.add(
          linkTo(
                  methodOn(BacklogAPI.class)
                      .commitToSprint(model.getContent().backlogItemId(), null))
              .withRel("commit-to-sprint")
              .withTitle("Commit to sprint")
              .withName("commitToSprint"));
    } else {
      model.add(
          linkToCurrentMapping()
              .slash("sprints")
              .slash(model.getContent().sprintId())
              .withRel("sprint"));
    }
    model.add(
        linkTo(methodOn(BacklogAPI.class).addComment(model.getContent().backlogItemId(), null))
            .withRel("add-comment")
            .withTitle("Add comment")
            .withName("addComment"));
    return model;
  }
}
