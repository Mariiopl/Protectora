package com.protectora.backend.services;

import com.protectora.backend.dto.TratamientoDto;
import com.protectora.backend.model.Empleado;
import com.protectora.backend.model.Mascota;
import com.protectora.backend.model.Tratamiento;
import com.protectora.backend.repository.EmpleadoRepository;
import com.protectora.backend.repository.MascotaRepository;
import com.protectora.backend.repository.TratamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de la lógica de negocio de los tratamientos veterinarios.
 * Permite crear, actualizar, consultar, marcar estados y eliminar tratamientos.
 */
@Service
public class TratamientoService {

    private final TratamientoRepository tratamientoRepository;
    private final MascotaRepository mascotaRepository;
    private final EmpleadoRepository empleadoRepository;

    public TratamientoService(TratamientoRepository tratamientoRepository,
            MascotaRepository mascotaRepository,
            EmpleadoRepository empleadoRepository) {
        this.tratamientoRepository = tratamientoRepository;
        this.mascotaRepository = mascotaRepository;
        this.empleadoRepository = empleadoRepository;
    }

    // =================================================
    // CONSULTAS
    // =================================================

    /**
     * Obtiene todos los tratamientos existentes.
     * 
     * @return Lista de todos los tratamientos.
     */
    public List<Tratamiento> findAll() {
        return tratamientoRepository.findAll();
    }

    /**
     * Obtiene un tratamiento por su ID.
     * 
     * @param id ID del tratamiento
     * @return Optional con el tratamiento si existe.
     */
    public Optional<Tratamiento> findById(Integer id) {
        return tratamientoRepository.findById(id);
    }

    /**
     * Obtiene todos los tratamientos asociados a una mascota específica.
     * 
     * @param idMascota ID de la mascota
     * @return Lista de tratamientos de la mascota.
     */
    public List<Tratamiento> findByMascotaId(Integer idMascota) {
        return tratamientoRepository.findByMascota_IdMascota(idMascota);
    }

    // =================================================
    // CREACIÓN / ACTUALIZACIÓN
    // =================================================

    /**
     * Crea un tratamiento a partir de un DTO.
     * 
     * @param dto Datos del tratamiento
     * @return Tratamiento creado y guardado en la base de datos.
     */
    public Tratamiento saveFromDTO(TratamientoDto dto) {
        Mascota mascota = mascotaRepository.findById(dto.getIdMascota())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        Empleado veterinario = empleadoRepository.findById(dto.getIdVeterinario())
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        Tratamiento tratamiento = Tratamiento.builder()
                .mascota(mascota)
                .tipo(dto.getTipo())
                .descripcion(dto.getDescripcion())
                .fecha(dto.getFecha())
                .veterinario(veterinario)
                .estado(dto.getEstado() != null ? dto.getEstado() : Tratamiento.Estado.pendiente)
                .build();

        return tratamientoRepository.save(tratamiento);
    }

    /**
     * Actualiza un tratamiento existente a partir de un DTO.
     * 
     * @param id  ID del tratamiento
     * @param dto Datos actualizados
     * @return Tratamiento actualizado
     */
    public Tratamiento updateFromDTO(Integer id, TratamientoDto dto) {
        return tratamientoRepository.findById(id)
                .map(trat -> {
                    Mascota mascota = mascotaRepository.findById(dto.getIdMascota())
                            .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
                    Empleado veterinario = empleadoRepository.findById(dto.getIdVeterinario())
                            .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

                    trat.setMascota(mascota);
                    trat.setTipo(dto.getTipo());
                    trat.setDescripcion(dto.getDescripcion());
                    trat.setFecha(dto.getFecha());
                    trat.setVeterinario(veterinario);
                    trat.setEstado(dto.getEstado() != null ? dto.getEstado() : trat.getEstado());
                    return tratamientoRepository.save(trat);
                }).orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
    }

    // =================================================
    // ESTADOS
    // =================================================

    /**
     * Marca un tratamiento como informado.
     * 
     * @param id ID del tratamiento
     * @return Tratamiento actualizado con estado 'informado'.
     */
    public Tratamiento marcarInformado(Integer id) {
        return tratamientoRepository.findById(id)
                .map(trat -> {
                    trat.setEstado(Tratamiento.Estado.informado);
                    return tratamientoRepository.save(trat);
                }).orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
    }

    /**
     * Marca un tratamiento como realizado.
     * 
     * @param id ID del tratamiento
     * @return Tratamiento actualizado con estado 'realizado'.
     */
    public Tratamiento marcarRealizado(Integer id) {
        return tratamientoRepository.findById(id)
                .map(trat -> {
                    trat.setEstado(Tratamiento.Estado.realizado);
                    return tratamientoRepository.save(trat);
                }).orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
    }

    // =================================================
    // ELIMINACIÓN
    // =================================================

    /**
     * Elimina un tratamiento por su ID.
     * 
     * @param id ID del tratamiento a eliminar
     */
    public void deleteById(Integer id) {
        tratamientoRepository.deleteById(id);
    }
}
