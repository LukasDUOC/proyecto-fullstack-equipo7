package cl.duoc.api_contenido.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contenido")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Contenido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String genero;
    @Column(nullable = false, length = 2000)
    private String sinopsis;
    @Column(nullable = false)
    private String duracion;
   @Column(nullable = false)
    private String tipo;
    @Column(nullable = false)
    private String visualizar;
   @Column(nullable = false)
    private LocalDate fechaLan;
    

    
}
