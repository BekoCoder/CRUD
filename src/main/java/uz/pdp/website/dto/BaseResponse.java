package uz.pdp.website.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
    private String message;
    private Integer status;
    private boolean success;
}
