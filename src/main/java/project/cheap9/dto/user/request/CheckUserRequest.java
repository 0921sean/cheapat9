package project.cheap9.dto.user.request;

import lombok.Data;

@Data
public class CheckUserRequest {
    private String id;
    private String password;
}
