package cl.duoc.api_recomendaciones.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recomendaciones")
public class Recomendacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long ratingId;

    private Long usuarioId;

    private Long contenidoId;

    private String genero;

    private Double puntuacion;

    private String mensaje;
}