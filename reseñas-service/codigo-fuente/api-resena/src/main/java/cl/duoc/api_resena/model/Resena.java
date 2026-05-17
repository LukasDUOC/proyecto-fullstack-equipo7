package cl.duoc.api_resena.model;


import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "resenas")
@NoArgsConstructor
@AllArgsConstructor
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(columnDefinition = "TEXT", nullable = false, length = 500)
    private String comentario; 

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Long contenidoId;

    @Column(nullable = false)
    private LocalDateTime fechaRating;

    @PrePersist
    public void prePersist() {
        this.fechaRating = LocalDateTime.now();
    }

}