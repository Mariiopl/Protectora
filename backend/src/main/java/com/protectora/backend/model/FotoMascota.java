package com.protectora.backend.model;

// ==================== FOTOS MASCOTAS ====================
import jakarta.persistence.*;
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
    private Mascota mascota;

    @Column(name = "url_imagen")
    private String urlImagen;
}
