package project.cheap9.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.cheap9.domain.Feedback;
import project.cheap9.service.FeedbackService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FeedbackApiController {

    private final FeedbackService feedbackService;

    /**
     * 피드백 정보 저장하기
     */
    @PostMapping("/api/feedbacks")
    public CreateFeedbackResponse saveFeedback(@RequestBody @Valid CreateFeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setContent(request.getContent());

        Long id = feedbackService.saveFeedback(feedback);
        return new CreateFeedbackResponse(id);
    }

    /**
     * 전체 피드백 조회
     */
    @GetMapping("/api/admin/feedbacks")
    public List<FeedbackDto> feedbacks() {
        List<Feedback> feedbacks = feedbackService.findFeedbacks();
        List<FeedbackDto> result = feedbacks.stream()
                .map(f -> new FeedbackDto(f))
                .collect(Collectors.toList());
        return result;
    }

//    + 필요한 재료들
    /**
     * 입력값
     */
    @Data
    static class CreateFeedbackRequest {
        private String content;
    }

    /**
     * 출력값
     */
    @Data
    static class CreateFeedbackResponse {
        private Long id;

        public CreateFeedbackResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class FeedbackDto {
        private Long id;
        private String content;

        public FeedbackDto(Feedback feedback) {
            this.id = feedback.getId();
            this.content = feedback.getContent();
        }
    }
}
