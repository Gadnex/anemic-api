package net.binarypaper.anemic_api.backlog.infrastructure.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import net.binarypaper.anemic_api.backlog.domain.ListBacklogItemResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class ListBacklogItemResponseProcessor
    implements RepresentationModelProcessor<EntityModel<ListBacklogItemResponse>> {
  @Override
  public EntityModel<ListBacklogItemResponse> process(EntityModel<ListBacklogItemResponse> model) {
    model.add(
        linkTo(methodOn(BacklogAPI.class).getBacklogItem(model.getContent().backlogItemId()))
            .withSelfRel());
    return model;
  }
}
