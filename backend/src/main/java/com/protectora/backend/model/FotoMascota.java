package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FotosMascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FotoMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Integer idFoto;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    @NotNull(message = "La mascota es obligatoria")
    private Mascota mascota;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Size(max = 255, message = "La URL no puede superar los 255 caracteres")
    @Column(name = "url_imagen")
    private String urlImagen;
}
// {
// "mascota": {
// "idMascota": 1
// },
// "urlImagen": "https://example.com/fotos/firulais.jpg"
// }
