package cl.duoc.api_ratings.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
@Data
@NoArgsConstructor
@AllArgsConstructor     


public class RatingCreateDTO {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El ID del contenido es obligatorio")
    private Long idContenido;

    @NotNull(message = "La puntuación es obligatoria")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private BigDecimal puntuacion;
    
}
