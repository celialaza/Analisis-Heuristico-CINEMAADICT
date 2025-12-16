package org.example.hibernate.copia;



import jakarta.persistence.*;
import lombok.*;
import org.example.hibernate.pelicula.Pelicula;
import org.example.hibernate.usuario.Usuario;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "copia")
public class Copia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String estado;
    private String soporte;

    // Relación: Muchas copias pertenecen a UN usuario
    @ManyToOne(fetch = FetchType.LAZY) // LAZY = solo carga el usuario si se lo pides
    @JoinColumn(name = "id_usuario", nullable = false) // Columna de la clave foránea
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

    // Relación: Muchas copias son de UNA película
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula", nullable = false) // Columna de la clave foránea
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Pelicula pelicula;
}
