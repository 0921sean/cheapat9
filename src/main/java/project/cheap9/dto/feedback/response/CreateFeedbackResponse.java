package project.cheap9.dto.feedback.response;

import lombok.Data;

@Data
public class CreateFeedbackResponse {
    private Long id;

    public CreateFeedbackResponse(Long id) {
        this.id = id;
    }
}
