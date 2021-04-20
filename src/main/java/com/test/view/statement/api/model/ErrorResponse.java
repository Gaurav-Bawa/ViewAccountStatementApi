package com.test.view.statement.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NonNull
    private String message;

}
