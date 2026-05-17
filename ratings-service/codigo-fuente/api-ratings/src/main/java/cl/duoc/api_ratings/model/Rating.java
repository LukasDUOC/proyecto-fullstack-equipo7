package cl.duoc.api_ratings.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "ratings")
@NoArgsConstructor
@AllArgsConstructor


public class Rating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private Long idContenido;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal puntuacion;

    @Column(nullable = false)
    private LocalDateTime fechaRating;

    @PrePersist
    public void prePersist() {
        this.fechaRating = LocalDateTime.now();
    }

}
