package cl.duoc.api_lista.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lista")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Lista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private String titulo;

    @ElementCollection
    @Column(name = "contenido_id")
    private List<Long> contenidosId;
    
    
    
    
}
