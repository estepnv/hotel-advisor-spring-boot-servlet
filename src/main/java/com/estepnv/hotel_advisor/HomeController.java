package com.estepnv.hotel_advisor;

import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class Whoami {
    public String id;

    Whoami(){
        this.id = "nobody";
    }
}


@RestController
public class HomeController {

    @GetMapping("/whoami")
    EntityModel<Whoami> whoami() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var details = (UserDetails)authentication.getDetails();

        var model = new Whoami();
        model.id = details.getUsername();

        return EntityModel.of(model);
    }
}
