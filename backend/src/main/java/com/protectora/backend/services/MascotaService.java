package com.protectora.backend.services;

import com.protectora.backend.dto.MascotaDto;
import com.protectora.backend.model.Mascota;
import com.protectora.backend.repository.MascotaRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final String uploadDir = "I:/Protectora/frontend/src/assets/mascotas/";

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
        new File(uploadDir).mkdirs();
    }

    public List<Mascota> getAll() {
        return mascotaRepository.findAll();
    }

    public Mascota getById(Integer id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
    }

    public String create(MascotaDto dto, MultipartFile foto) {
        Mascota mascota = dto.toEntity();
        mascota.setFechaIngreso(LocalDate.now());
        mascota.setEstadoAdopcion(Mascota.EstadoAdopcion.adoptable);
        mascota = mascotaRepository.save(mascota);

        if (foto != null && !foto.isEmpty()) {
            String fileName = guardarImagen(foto);
            mascota.setFoto(fileName);
            mascotaRepository.save(mascota);
        }

        return "Mascota creada correctamente";
    }

    public String update(Integer id, MascotaDto dto, MultipartFile foto) {
        Mascota mascota = getById(id);
        dto.updateEntity(mascota);

        if (foto != null && !foto.isEmpty()) {
            String fileName = guardarImagen(foto);
            mascota.setFoto(fileName);
        }

        mascotaRepository.save(mascota);
        return "Mascota actualizada correctamente";
    }

    public String delete(Integer id) {
        Mascota mascota = getById(id);
        mascotaRepository.delete(mascota);
        return "Mascota eliminada correctamente";
    }

    public Resource getImage(String filename) throws IOException {
        Path file = Paths.get(uploadDir).resolve(filename);
        if (!Files.exists(file)) {
            throw new RuntimeException("La imagen no existe");
        }
        return new UrlResource(file.toUri());
    }

    private String guardarImagen(MultipartFile foto) {
        try {
            String nombreArchivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            File destino = new File(uploadDir + nombreArchivo);
            foto.transferTo(destino);
            return nombreArchivo;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    public List<Mascota> getAdoptable() {
        return mascotaRepository.findByEstadoAdopcion(Mascota.EstadoAdopcion.adoptable);
    }

}
