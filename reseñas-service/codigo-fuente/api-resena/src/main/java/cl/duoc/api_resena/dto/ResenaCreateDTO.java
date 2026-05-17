package cl.duoc.api_resena.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor     


public class ResenaCreateDTO {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID del contenido es obligatorio")
    private Long contenidoId;

    @NotNull(message = "La reseña es obligatoria")
    @Size(min = 1, max = 500, message = "La reseña debe tener entre 1 y 500 caracteres")
    private String comentario; 




    
}
