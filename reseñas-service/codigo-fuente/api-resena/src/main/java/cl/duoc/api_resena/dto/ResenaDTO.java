package cl.duoc.api_resena.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResenaDTO {
    private Long id;
    private String comentario; 
    private Long usuarioId;
    private Long contenidoId;
    private LocalDateTime fechaRating;
    
}
