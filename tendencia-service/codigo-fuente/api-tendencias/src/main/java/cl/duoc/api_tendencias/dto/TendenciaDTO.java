package cl.duoc.api_tendencias.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TendenciaDTO {

    private Long id;

    private String titulo;

    private String genero;

    private String tipo;

    private BigDecimal promedio;

    private Integer cantidadRatings;
}