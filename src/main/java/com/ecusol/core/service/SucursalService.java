package com.ecusol.core.service;

import com.ecusol.core.dto.FeriadoDTO;
import com.ecusol.core.dto.SucursalRequestDTO;
import com.ecusol.core.model.Feriado;
import com.ecusol.core.model.Sucursal;
import com.ecusol.core.repository.SucursalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepo;

    // 1. Crear Sucursal (Sin feriados)
    public Sucursal crearSucursal(SucursalRequestDTO dto) {
        log.info("Iniciando creación de sucursal: {}", dto.getName());
        Sucursal s = new Sucursal();
        s.setName(dto.getName());
        s.setEmailAddress(dto.getEmailAddress());
        s.setPhoneNumber(dto.getPhoneNumber());
        s.setState("ACTIVE");
        s.setCreationDate(LocalDateTime.now());
        s.setLastModifiedDate(LocalDateTime.now());
        s.setBranchHolidays(new ArrayList<>());

        Sucursal guardada = sucursalRepo.save(s);
        log.info("Sucursal creada con ID: {}", guardada.getId());
        return guardada;
    }

    // 2. Modificar Sucursal (Solo teléfono y fecha modif)
    public Sucursal modificarSucursal(String id, String nuevoTelefono) {
        log.info("Actualizando teléfono para sucursal ID: {}", id);
        Sucursal s = sucursalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        s.setPhoneNumber(nuevoTelefono);
        s.setLastModifiedDate(LocalDateTime.now());

        return sucursalRepo.save(s);
    }

    // 3. Crear Feriado
    public Sucursal agregarFeriado(String sucursalId, FeriadoDTO feriadoDto) {
        log.info("Agregando feriado {} a sucursal {}", feriadoDto.getDate(), sucursalId);
        Sucursal s = sucursalRepo.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        Feriado f = new Feriado(feriadoDto.getDate(), feriadoDto.getName());

        if (s.getBranchHolidays() == null) {
            s.setBranchHolidays(new ArrayList<>());
        }
        s.getBranchHolidays().add(f);
        s.setLastModifiedDate(LocalDateTime.now()); // Buena práctica actualizar fecha mod

        return sucursalRepo.save(s);
    }

    // 4. Eliminar Feriado
    public void eliminarFeriado(String sucursalId, LocalDate fecha) {
        log.info("Eliminando feriado fecha {} de sucursal {}", fecha, sucursalId);
        Sucursal s = sucursalRepo.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        if (s.getBranchHolidays() != null) {
            s.getBranchHolidays().removeIf(h -> h.getDate().equals(fecha));
        }
        s.setLastModifiedDate(LocalDateTime.now());
        sucursalRepo.save(s);
    }

    // 5. Validar si es feriado
    public boolean esFeriado(String sucursalId, LocalDate fecha) {
        log.info("Verificando si {} es feriado en {}", fecha, sucursalId);
        Sucursal s = sucursalRepo.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        if (s.getBranchHolidays() == null) return false;

        return s.getBranchHolidays().stream()
                .anyMatch(h -> h.getDate().equals(fecha));
    }

    public List<Sucursal> listarTodo() {
        return sucursalRepo.findAll();
    }

    public Optional<Sucursal> buscarPorId(String id) {
        return sucursalRepo.findById(id);
    }
}