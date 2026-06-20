package cl.duoc.api_lista.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ListaCreateDTO {
    @Schema(description = "ID de usuario para agregar la lista.", example = "1")
    @NotBlank(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
    @Schema(description = "Tiulo del la Pelicula o Serie.", example = "Lista de peliculas para ver mas tarde.")
    @NotBlank(message = "El nombre es obligatorio")
    private String titulo;
    @Schema(description = "IDs de los contenido para agregar a la lista", example = "[1,2,4,9,10]")
    @NotBlank(message = "Los IDs de los contenidos son obligatorios")
    private List<Long> contenidosId;

   
}