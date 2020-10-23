package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Deprecated // USE HAL
public class PageMetadata {
    private Integer number;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
}
