package org.example.hibernate.pelicula;

import jakarta.persistence.*;
import lombok.*;
import org.example.hibernate.copia.Copia;

import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;
    private String genero;
    private int año;
    private String descripcion;
    private String director;

    // Relación: Una película puede estar en muchas copias
    @OneToMany(mappedBy = "pelicula")

    //Excluye este atributo en método toString para que no de errores
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Copia> copias;

    @Override
    public String toString() {
        return titulo + " (" + año + ")";
    }


}
