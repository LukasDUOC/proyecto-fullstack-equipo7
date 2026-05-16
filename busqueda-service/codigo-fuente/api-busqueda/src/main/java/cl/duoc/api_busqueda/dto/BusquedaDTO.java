package cl.duoc.api_busqueda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BusquedaDTO {
    private Long id;
    private Long contenidoId;
    private String titulo;

    
    
}
