package project.cheap9.dto.item.response;

import lombok.Data;

@Data
public class UpdateItemResponse {
    private Long id;

    public UpdateItemResponse(Long id) {
        this.id = id;
    }
}
