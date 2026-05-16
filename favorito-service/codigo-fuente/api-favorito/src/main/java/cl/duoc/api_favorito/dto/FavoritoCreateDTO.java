package cl.duoc.api_favorito.dto;



import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoCreateDTO {

    @NotNull(message= "debe ingresar el id del usuario")
    private Long usuarioId;

    @NotNull(message= "debe ingresar el id del contenido")
    private long contenidoId;
    
    @NotNull(message= "debe ingresar el favorito")
    private boolean favorito;
    
}
