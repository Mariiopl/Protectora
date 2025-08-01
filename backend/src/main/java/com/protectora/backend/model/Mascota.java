package com.protectora.backend.model;

// ==================== MASCOTAS ====================
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;

    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;

    @Enumerated(EnumType.STRING)
    private Tamano tamaño;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column(name = "carácter")
    private String caracter;

    @Column(name = "necesidades_especiales")
    private String necesidadesEspeciales;

    private Boolean esterilizado;
    private Boolean vacunado;
    private Boolean desparasitado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_adopción")
    private EstadoAdopcion estadoAdopcion = EstadoAdopcion.adoptable;

    private String historia;
    private String ubicación;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @OneToMany(mappedBy = "mascota")
    private List<FotoMascota> fotos;

    @OneToMany(mappedBy = "mascota")
    private List<Adopcion> adopciones;

    @OneToMany(mappedBy = "mascota")
    private List<Apadrinamiento> apadrinamientos;

    @OneToMany(mappedBy = "mascota")
    private List<Tratamiento> tratamientos;

    public enum Tamano {
        pequeño, mediano, grande
    }

    public enum Sexo {
        macho, hembra
    }

    public enum EstadoAdopcion {
        adoptable, en_proceso, adoptado
    }
}
