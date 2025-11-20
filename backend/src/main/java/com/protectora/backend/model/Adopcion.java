package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Adopciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adopcion")
    private Integer idAdopcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @NotNull(message = "El usuario es obligatorio")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    @NotNull(message = "La mascota es obligatoria")
    private Mascota mascota;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado es obligatorio")
    private Estado estado = Estado.pendiente;

    @Column(name = "fecha_solicitud")
    @NotNull(message = "La fecha de solicitud es obligatoria")
    private LocalDate fechaSolicitud;

    @Column(name = "fecha_adopcion")
    private LocalDate fechaAdopcion;

    @Size(max = 1000, message = "La experiencia no puede superar los 1000 caracteres")
    private String experiencia;

    @Column(name = "tipo_vivienda")
    @Size(max = 200, message = "El tipo de vivienda no puede superar los 200 caracteres")
    private String tipoVivienda;

    @Size(max = 1000, message = "Los comentarios no pueden superar los 1000 caracteres")
    private String comentarios;

    @OneToMany(mappedBy = "adopcion")
    private List<Seguimiento> seguimientos;

    public enum Estado {
        pendiente, en_evaluación, aceptada, rechazada, finalizada
    }
}
// {
// "usuario": {
// "idUsuario": 1
// },
// "mascota": {
// "idMascota": 2
// },
// "estado": "pendiente",
// "fechaSolicitud": "2025-09-19",
// "fechaAdopcion": null,
// "experiencia": "Tengo experiencia cuidando perros y gatos.",
// "tipoVivienda": "Piso con terraza",
// "comentarios": "Me gustaría adoptar a Firulais porque es muy activo."
// }
