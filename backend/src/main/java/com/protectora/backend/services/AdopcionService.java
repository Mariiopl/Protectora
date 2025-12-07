package com.protectora.backend.services;

import com.protectora.backend.dto.AdopcionDto;
import com.protectora.backend.dto.SolicitudAdopcionDto;
import com.protectora.backend.model.Adopcion;
import com.protectora.backend.model.Adopcion.Estado;
import com.protectora.backend.model.Mascota;
import com.protectora.backend.model.Mascota.EstadoAdopcion;
import com.protectora.backend.model.Usuario;
import com.protectora.backend.repository.AdopcionRepository;
import com.protectora.backend.repository.MascotaRepository;
import com.protectora.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdopcionService {

    private final AdopcionRepository adopcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;

    public AdopcionService(AdopcionRepository adopcionRepository,
            UsuarioRepository usuarioRepository,
            MascotaRepository mascotaRepository) {
        this.adopcionRepository = adopcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
    }

    // ======================
    // CONSULTAS
    // ======================
    public List<AdopcionDto> findAllDTO() {
        return adopcionRepository.findAll()
                .stream()
                .map(AdopcionDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AdopcionDto> findByUsuarioDTO(Integer idUsuario) {
        return adopcionRepository.findByUsuario_IdUsuario(idUsuario)
                .stream()
                .map(AdopcionDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Adopcion getById(Integer id) {
        return adopcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adopción no encontrada"));
    }

    // ======================
    // CREACIÓN / ACTUALIZACIÓN
    public Adopcion create(Integer idUsuario, Integer idMascota, AdopcionDto dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        // Cambiamos el estado de la mascota a EN_PROCESO
        mascota.setEstadoAdopcion(Mascota.EstadoAdopcion.en_proceso);
        mascotaRepository.save(mascota); // Guardamos el cambio

        Adopcion adopcion = new Adopcion();
        adopcion.setUsuario(usuario);
        adopcion.setMascota(mascota);
        adopcion.setEstado(dto.estado() != null ? dto.estado() : Adopcion.Estado.pendiente);
        adopcion.setFechaSolicitud(LocalDate.now());
        adopcion.setFechaAdopcion(dto.fechaAdopcion());
        adopcion.setExperiencia(dto.experiencia());
        adopcion.setTipoVivienda(dto.tipoVivienda());
        adopcion.setComentarios(dto.comentarios());

        return adopcionRepository.save(adopcion);
    }

    public Adopcion update(Integer idAdopcion, AdopcionDto dto) {
        Adopcion adopcion = getById(idAdopcion);

        if (dto.estado() != null)
            adopcion.setEstado(dto.estado());
        if (dto.fechaAdopcion() != null)
            adopcion.setFechaAdopcion(dto.fechaAdopcion());
        if (dto.experiencia() != null)
            adopcion.setExperiencia(dto.experiencia());
        if (dto.tipoVivienda() != null)
            adopcion.setTipoVivienda(dto.tipoVivienda());
        if (dto.comentarios() != null)
            adopcion.setComentarios(dto.comentarios());

        return adopcionRepository.save(adopcion);
    }

    public void delete(Integer idAdopcion) {
        Adopcion adopcion = getById(idAdopcion);
        adopcionRepository.delete(adopcion);
    }

    public List<AdopcionDto> obtenerPendientes() {
        return adopcionRepository.obtenerPendientes();
    }

    public void cambiarEstado(Integer idAdopcion, Adopcion.Estado nuevoEstado) {
        Adopcion adopcion = adopcionRepository.findById(idAdopcion)
                .orElseThrow(() -> new RuntimeException("Adopción no encontrada"));

        // Cambiar estado de la adopción
        adopcion.setEstado(nuevoEstado);
        adopcionRepository.save(adopcion);

        // Cambiar estado de la mascota asociada
        Mascota mascota = adopcion.getMascota();
        if (mascota != null) {
            switch (nuevoEstado) {
                case aceptada: // o el nombre que uses para "aceptada"
                    mascota.setEstadoAdopcion(Mascota.EstadoAdopcion.adoptado); // o el enum que tengas
                    break;
                case rechazada: // si quieres manejar rechazadas
                    mascota.setEstadoAdopcion(Mascota.EstadoAdopcion.adoptable);
                    break;
                default:
                    // otros estados si es necesario
            }
            mascotaRepository.save(mascota);
        }
    }

}
