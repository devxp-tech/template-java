package devxp-tech.apptemplate.controller;

import devxp-tech.apptemplate.model.UserREQ;
import devxp-tech.apptemplate.model.UserRES;
import devxp-tech.apptemplate.service.UserService;
import devxp-tech.bootstrap.openapi.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Ok
    @Unauthorized
    @InternalServerError
    @SecurityRequirement(name = "bearerAuth")
    public List<UserRES> listAll() {
        return userService.listAll();
    }

    @GetMapping("/{userId}")
    @Ok
    @Unauthorized
    @NotFound
    @UnprocessableEntity
    @InternalServerError
    @SecurityRequirement(name = "bearerAuth")
    public UserRES findById(@NotNull @PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @PostMapping
    @Ok
    @Unauthorized
    @UnprocessableEntity
    @InternalServerError
    @SecurityRequirement(name = "bearerAuth")
    public UserRES save(@Valid @RequestBody UserREQ userREQ) {
        return userService.save(userREQ);
    }
}
