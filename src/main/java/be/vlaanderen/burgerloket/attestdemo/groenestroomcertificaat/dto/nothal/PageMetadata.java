package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Preferably use HAL implementations to avoid errors in generating
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageMetadata {
    private Integer number;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
}
