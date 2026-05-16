package cl.duoc.api_recomendaciones.dto;

import lombok.Data;

@Data
public class RecomendacionCreateDTO {
  
    private Long usuarioId;
    private Long contenidoId;
    private Long ratingId;
}
