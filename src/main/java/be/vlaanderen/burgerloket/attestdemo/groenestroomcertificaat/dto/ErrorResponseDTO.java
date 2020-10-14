package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    private String title;
    private String detail;
    private Integer status;
}
