package project.cheap9.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.cheap9.dto.user.request.CheckUserRequest;
import project.cheap9.dto.user.response.CheckUserResponse;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    @Value("${admin.id}")
    private String adminId;
    @Value("${admin.password}")
    private String adminPassword;

    @PostMapping("/api/check")
    public CheckUserResponse checkUser(@RequestBody @Valid CheckUserRequest request) {
        if (request.getId().equals(adminId) && request.getPassword().equals(adminPassword)) {
            return new CheckUserResponse("True");
        }
        return new CheckUserResponse("False");
    }

}
