package cl.duoc.api_filtros.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContenidoDTO {

    private Long id;
    private String titulo;
    private String genero;
    private String sinopsis;
    private String duracion;
    private String tipo;
    private String visualizar;
    private LocalDate fechaLan;

}