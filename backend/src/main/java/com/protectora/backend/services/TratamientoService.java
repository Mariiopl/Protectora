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

    public List<Tratamiento> findAll() {
        return tratamientoRepository.findAll();
    }

    public Optional<Tratamiento> findById(Integer id) {
        return tratamientoRepository.findById(id);
    }

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

    public void deleteById(Integer id) {
        tratamientoRepository.deleteById(id);
    }

    public List<Tratamiento> findByMascotaId(Integer idMascota) {
        return tratamientoRepository.findByMascota_IdMascota(idMascota);
    }

    public Tratamiento marcarInformado(Integer id) {
        return tratamientoRepository.findById(id)
                .map(trat -> {
                    trat.setEstado(Tratamiento.Estado.informado);
                    return tratamientoRepository.save(trat);
                }).orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
    }

    public Tratamiento marcarRealizado(Integer id) {
        return tratamientoRepository.findById(id)
                .map(trat -> {
                    trat.setEstado(Tratamiento.Estado.realizado); // usar enum
                    return tratamientoRepository.save(trat);
                }).orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
    }

}
