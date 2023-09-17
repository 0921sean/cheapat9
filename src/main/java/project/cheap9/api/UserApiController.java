package project.cheap9.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 입력값
     */
    @Data
    static class CheckUserRequest {
        private String id;
        private String password;
    }

    /**
     * 출력값
     */
    @Data
    static class CheckUserResponse {
        private String result;

        public CheckUserResponse(String result) {
            this.result = result;
        }
    }
}
