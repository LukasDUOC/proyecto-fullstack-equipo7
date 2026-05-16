package cl.duoc.api_recomendaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    private Long id;
    private Long idUsuario;
    private Long idContenido;
    private double puntuacion;
    private LocalDateTime fechaRating;


}