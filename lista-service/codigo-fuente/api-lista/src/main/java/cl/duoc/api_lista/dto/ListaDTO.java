package cl.duoc.api_lista.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor


public class ListaDTO {
    private Long id;

    private Long usuarioId;

    private String titulo;

    private List<ContenidoDTO> contenidos;
    
}
