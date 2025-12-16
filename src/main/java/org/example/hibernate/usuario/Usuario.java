package org.example.hibernate.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.example.hibernate.copia.Copia;

import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;

    private String nombre_usuario;
    private String contraseña;

    // Relación: Un usuario puede tener muchas copias
    // 'mappedBy = "usuario"' indica que la clave foránea está en la clase Copia
    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Copia> copias;
}
