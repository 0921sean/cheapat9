package project.cheap9.dto.user.response;

import lombok.Data;

@Data
public class CheckUserResponse {
    private String result;

    public CheckUserResponse(String result) {
        this.result = result;
    }
}
