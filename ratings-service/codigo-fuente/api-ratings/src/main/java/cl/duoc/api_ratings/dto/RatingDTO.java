package cl.duoc.api_ratings.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    private Long id;
    private Long idUsuario;
    private Long idContenido;
    private BigDecimal puntuacion;
    private LocalDateTime fechaRating;


}
