package com.estepnv.hotel_advisor.users;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserViewModel> {
    public UserViewModel toModel(User user) {
        var model = new UserViewModel(user);
        model.add(linkTo(methodOn(UserController.class).show(user.getId())).withSelfRel());
        return model;
    }

    public PagedModel<UserViewModel> toPagedModel(Page<User> users) {
        var pageable = users.getPageable();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                pageable.getPageSize(),
                pageable.getPageNumber(),
                users.getTotalElements(),
                users.getTotalPages()
        );

        var userModels = users.stream().map(user -> toModel(user)).toList();

        var model = PagedModel.of(userModels, pageMetadata);
        model.add(linkTo(methodOn(UserController.class).listUsers(users.getPageable())).withSelfRel());

        return model;
    }
}
