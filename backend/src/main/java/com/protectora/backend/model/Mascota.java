package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Size(max = 50, message = "La especie no puede superar los 50 caracteres")
    private String especie;

    @NotBlank(message = "La raza es obligatoria")
    @Size(max = 50, message = "La raza no puede superar los 50 caracteres")
    private String raza;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 50, message = "La edad no puede superar los 50 años")
    private Integer edad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tamaño") // así se mantiene en la BD con ñ
    @com.fasterxml.jackson.annotation.JsonProperty("tamano") // así se serializa a JSON sin ñ
    private Tamano tamano;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column(name = "caracter")
    @Size(max = 200, message = "El carácter no puede superar los 200 caracteres")
    private String caracter;

    @Column(name = "necesidades_especiales")
    @Size(max = 500, message = "Las necesidades especiales no pueden superar los 500 caracteres")
    private String necesidadesEspeciales;

    private Boolean esterilizado;
    private Boolean vacunado;
    private Boolean desparasitado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_adopcion")
    private EstadoAdopcion estadoAdopcion = EstadoAdopcion.adoptable;

    @Column(name = "historia")
    @Size(max = 1000, message = "La historia no puede superar los 1000 caracteres")
    private String historia;

    @Column(name = "ubicacion")
    @Size(max = 200, message = "La ubicación no puede superar los 200 caracteres")
    private String ubicacion;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "foto")
    @JsonIgnoreProperties({ "mascota", "hibernateLazyInitializer", "handler" })
    private String foto;

    @OneToMany(mappedBy = "mascota")
    @JsonIgnore
    private List<Adopcion> adopciones;

    @OneToMany(mappedBy = "mascota")
    @JsonIgnore
    private List<Apadrinamiento> apadrinamientos;

    @OneToMany(mappedBy = "mascota")
    @JsonIgnore
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
// {
// "nombre": "Firulais",
// "especie": "Perro",
// "raza": "Labrador",
// "edad": 3,
// "tamaño": "grande",
// "sexo": "macho",
// "carácter": "Muy juguetón y amigable",
// "necesidadesEspeciales": "Ninguna",
// "esterilizado": true,
// "vacunado": true,
// "desparasitado": true,
// "estadoAdopcion": "adoptable",
// "historia": "Llegó desde un refugio en malas condiciones, pero ahora está
// sano y feliz",
// "ubicación": "Refugio Central",
// "fechaIngreso": "2025-09-19"
// }
