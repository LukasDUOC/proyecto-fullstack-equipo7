package cl.duoc.api_busqueda.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BusquedaCreateDTO {

    @Schema(description ="ID de la Pelicula/Serie (debe existir en api-contenido).", example ="4")
    @NotNull(message = "El ID del contenido es obligatorio")
    private Long contenidoId;

    @Schema (description = "El titulo de la Pelicula/Serie. ",example ="Toy Story 4" )
    @NotNull(message = "El título es obligatorio")
    private String titulo;


}
