package com.protectora.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(name = "contraseña")
    private String contrasena;

    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    @Column(name = "teléfono")
    private String telefono;

    @Column(name = "dirección")
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // Enum interno
    public enum TipoUsuario {
        adoptante, voluntario, empleado, administrador
    }

    // Se ejecuta antes de guardar un nuevo registro
    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}
// Ejemplo JSON para crear un usuario: (No hace falta poner la fecha de
// registro)
// {
// "nombre": "Laura",
// "apellido": "González",
// "email": "laura.gonzalez@example.com",
// "telefono": "+34123456789",
// "direccion": "Calle Falsa 123, Madrid",
// "tipoUsuario": "empleado",
// "contrasena": "MiPassSegura123"
// }
