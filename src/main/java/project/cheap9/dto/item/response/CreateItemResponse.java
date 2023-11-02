package project.cheap9.dto.item.response;

import lombok.Data;

@Data
public class CreateItemResponse {
    private Long id;

    public CreateItemResponse(Long id) {
        this.id = id;
    }
}
