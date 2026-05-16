package cl.duoc.api_recomendaciones.dto;

import lombok.Data;
@Data
public class RecomendacionDTO {

    private Long id;
    private Long usuarioId;
    private Long contenidoId;

    private String genero;
    private Double puntuacion;
    private String mensaje;
}