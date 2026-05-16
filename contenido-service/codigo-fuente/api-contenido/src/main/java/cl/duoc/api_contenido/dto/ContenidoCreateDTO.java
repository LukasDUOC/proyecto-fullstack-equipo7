package cl.duoc.api_contenido.dto;

import java.time.LocalDate;

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
    
    private String titulo;
    @NotBlank(message = "El genero no puede estar vacío")
    private String genero;
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 10 , message ="La sinopsis debe tener al menos 10 caracteres")
    private String sinopsis;
    @NotBlank(message = "La duracion no puede estar vacío")
    private String duracion;
    @NotBlank(message = "El tipo no puede estar vacío")
    private String tipo;
    @NotBlank(message = "La visualizacion no puede estar vacío")
    private String visualizar;
    @NotNull(message = "La fecha de lanzamiento es obligatoria")
    private LocalDate fechaLan;
    
}
