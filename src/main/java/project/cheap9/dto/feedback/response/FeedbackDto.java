package project.cheap9.dto.feedback.response;

import lombok.Data;
import project.cheap9.domain.Feedback;

@Data
public class FeedbackDto {
    private Long id;
    private String content;

    public FeedbackDto(Feedback feedback) {
        this.id = feedback.getId();
        this.content = feedback.getContent();
    }
}
