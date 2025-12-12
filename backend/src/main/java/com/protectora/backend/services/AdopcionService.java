package com.protectora.backend.services;

import com.protectora.backend.dto.AdopcionDto;
import com.protectora.backend.model.Adopcion;
import com.protectora.backend.model.Adopcion.Estado;
import com.protectora.backend.model.Mascota;
import com.protectora.backend.model.Usuario;
import com.protectora.backend.repository.AdopcionRepository;
import com.protectora.backend.repository.MascotaRepository;
import com.protectora.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para manejar la lógica de negocio relacionada con Adopciones.
 * Encapsula operaciones como creación, actualización, eliminación y consultas.
 */
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

    // =================================================
    // CONSULTAS
    // =================================================

    /**
     * Obtiene todas las adopciones y las transforma a DTO.
     * 
     * @return Lista de AdopcionDto
     */
    public List<AdopcionDto> findAllDTO() {
        return adopcionRepository.findAll()
                .stream()
                .map(AdopcionDto::fromEntity) // Transformación entidad -> DTO
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las adopciones de un usuario por ID y las transforma a DTO.
     * 
     * @param idUsuario ID del usuario
     * @return Lista de AdopcionDto
     */
    public List<AdopcionDto> findByUsuarioDTO(Integer idUsuario) {
        return adopcionRepository.findByUsuario_IdUsuario(idUsuario)
                .stream()
                .map(AdopcionDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una adopción por su ID.
     * 
     * @param id ID de la adopción
     * @return Objeto Adopcion
     * @throws RuntimeException si no se encuentra la adopción
     */
    public Adopcion getById(Integer id) {
        return adopcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adopción no encontrada"));
    }

    // =================================================
    // CREACIÓN DE ADOPCIÓN
    // =================================================

    /**
     * Crea una nueva adopción asociando un usuario y una mascota.
     * Cambia automáticamente el estado de la mascota a "en_proceso".
     *
     * @param idUsuario ID del usuario
     * @param idMascota ID de la mascota
     * @param dto       DTO con información adicional
     * @return La adopción creada
     */
    public Adopcion create(Integer idUsuario, Integer idMascota, AdopcionDto dto) {
        // Obtener usuario y mascota
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        // Cambiar estado de la mascota a "en_proceso"
        mascota.setEstadoAdopcion(Mascota.EstadoAdopcion.en_proceso);
        mascotaRepository.save(mascota);

        // Crear objeto adopción
        Adopcion adopcion = new Adopcion();
        adopcion.setUsuario(usuario);
        adopcion.setMascota(mascota);
        adopcion.setEstado(dto.estado() != null ? dto.estado() : Estado.pendiente);
        adopcion.setFechaSolicitud(LocalDate.now());
        adopcion.setFechaAdopcion(dto.fechaAdopcion());
        adopcion.setExperiencia(dto.experiencia());
        adopcion.setTipoVivienda(dto.tipoVivienda());
        adopcion.setComentarios(dto.comentarios());

        return adopcionRepository.save(adopcion);
    }

    // =================================================
    // ACTUALIZACIÓN DE ADOPCIÓN
    // =================================================

    /**
     * Actualiza los campos de una adopción existente si se proporcionan en el DTO.
     * 
     * @param idAdopcion ID de la adopción
     * @param dto        DTO con nuevos valores
     * @return La adopción actualizada
     */
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

    // =================================================
    // ELIMINACIÓN DE ADOPCIÓN
    // =================================================

    /**
     * Elimina una adopción por su ID.
     * 
     * @param idAdopcion ID de la adopción
     */
    public void delete(Integer idAdopcion) {
        Adopcion adopcion = getById(idAdopcion);
        adopcionRepository.delete(adopcion);
    }

    // =================================================
    // MÉTODOS ADICIONALES
    // =================================================

    /**
     * Obtiene adopciones pendientes usando la query custom en el repositorio.
     * 
     * @return Lista de AdopcionDto pendientes
     */
    public List<AdopcionDto> obtenerPendientes() {
        return adopcionRepository.obtenerPendientes();
    }

    /**
     * Cambia el estado de una adopción y actualiza también el estado de la mascota
     * asociada.
     * Si el estado es 'aceptada', se asigna la fecha de adopción.
     *
     * @param idAdopcion  ID de la adopción
     * @param nuevoEstado Nuevo estado de la adopción
     */
    public void cambiarEstado(Integer idAdopcion, Estado nuevoEstado) {
        Adopcion adopcion = adopcionRepository.findById(idAdopcion)
                .orElseThrow(() -> new RuntimeException("Adopción no encontrada"));

        // Cambiar estado de la adopción
        adopcion.setEstado(nuevoEstado);

        // Si es aceptada, asignar fecha de adopción
        if (nuevoEstado == Estado.aceptada) {
            adopcion.setFechaAdopcion(LocalDate.now());
        }
        adopcionRepository.save(adopcion);

        // Actualizar estado de la mascota asociada
        Mascota mascota = adopcion.getMascota();
        if (mascota != null) {
            switch (nuevoEstado) {
                case aceptada:
                    mascota.setEstadoAdopcion(Mascota.EstadoAdopcion.adoptado);
                    break;
                case rechazada:
                    mascota.setEstadoAdopcion(Mascota.EstadoAdopcion.adoptable);
                    break;
                default:
                    // Otros estados si se necesitan
            }
            mascotaRepository.save(mascota);
        }
    }

    /**
     * Obtiene todas las adopciones de un usuario específico.
     * 
     * @param usuario Objeto usuario
     * @return Lista de AdopcionDto
     */
    public List<AdopcionDto> getAdopcionesDeUsuario(Usuario usuario) {
        return adopcionRepository.findByUsuario(usuario)
                .stream()
                .map(AdopcionDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las adopciones existentes.
     * 
     * @return Lista de AdopcionDto
     */
    public List<AdopcionDto> getTodasAdopciones() {
        return adopcionRepository.findAll()
                .stream()
                .map(AdopcionDto::fromEntity)
                .toList();
    }

    /**
     * Obtiene todas las adopciones cuyo estado es 'aceptada'.
     * 
     * @return Lista de AdopcionDto aceptadas
     */
    public List<AdopcionDto> obtenerAceptadas() {
        return adopcionRepository.findByEstado(Estado.aceptada)
                .stream()
                .map(AdopcionDto::fromEntity)
                .toList();
    }
}
