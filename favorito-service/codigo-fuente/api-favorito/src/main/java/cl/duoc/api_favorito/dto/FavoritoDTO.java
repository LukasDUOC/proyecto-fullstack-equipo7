package cl.duoc.api_favorito.dto;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoDTO {

    private long id;
    private long usuarioId;
    private long contenidoId;
    private boolean favorito;
    
}
