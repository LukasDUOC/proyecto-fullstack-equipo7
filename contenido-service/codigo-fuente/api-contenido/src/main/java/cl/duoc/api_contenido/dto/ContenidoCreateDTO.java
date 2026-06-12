package cl.duoc.api_contenido.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContenidoCreateDTO {
    @Schema(description = "Tiulo del la Pelicula o Serie.", example = "Los Simpson")
    @NotBlank(message = "El nombre es obligatorio")
    private String titulo;

    @Schema(description = "Genero de la Pelicula o Serie.", example = "Comedia")
    @NotBlank(message = "El genero no puede estar vacío")
    private String genero;

    @Schema(description = "Una pequeña Sipnosis de la Pelicula o Serie.", example ="Los Simpson es una serie estadounidense que narra la vida cotidiana de una peculiar familia en el pueblo de Springfield")
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 10 , message ="La sinopsis debe tener al menos 10 caracteres")
    private String sinopsis;

    @Schema(description = "Duracion total de la Pelicula o Serie", example = "38 temporadas")
    @NotBlank(message = "La duracion no puede estar vacío")
    private String duracion;

    @Schema (description ="Si es Pelicula o Serie.", example ="Serie")
    @NotBlank(message = "El tipo no puede estar vacío")
    private String tipo;

    @Schema(description = "En que plataforma se puede ver.", example = "Disney +")
    @NotBlank(message = "La visualizacion no puede estar vacío")
    private String visualizar;

    @Schema(description = "Fecha de lanzamiento de la Pelicula o Serie." , example ="1989-12-17")
    @NotNull(message = "La fecha de lanzamiento es obligatoria")
    private LocalDate fechaLan;
    
}
