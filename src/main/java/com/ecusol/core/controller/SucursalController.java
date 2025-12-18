package com.ecusol.core.controller;

import com.ecusol.core.dto.FeriadoDTO;
import com.ecusol.core.dto.SucursalRequestDTO;
import com.ecusol.core.model.Feriado;
import com.ecusol.core.model.Sucursal;
import com.ecusol.core.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sucursales")
@Tag(name = "Gestión de Sucursales", description = "API Examen Arquitectura")
@Slf4j
public class SucursalController {

    @Autowired
    private SucursalService service;

    // 1. Obtener listado de todas las sucursales
    @GetMapping
    @Operation(summary = "Listar sucursales", description = "Obtiene todas las sucursales registradas")
    public ResponseEntity<List<Sucursal>> listarSucursales() {
        log.info("REST: Listar todas las sucursales");
        return ResponseEntity.ok(service.listarTodo());
    }

    // 2. Crear una Sucursal (sin feriados)
    @PostMapping
    @Operation(summary = "Crear sucursal", description = "Crea una nueva sucursal sin feriados iniciales")
    public ResponseEntity<Sucursal> crearSucursal(@Valid @RequestBody SucursalRequestDTO dto) {
        log.info("REST: Crear sucursal {}", dto);
        return new ResponseEntity<>(service.crearSucursal(dto), HttpStatus.CREATED);
    }

    // 3. Obtener sucursal por su ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene detalle de una sucursal específica")
    public ResponseEntity<Sucursal> obtenerPorId(@PathVariable String id) {
        log.info("REST: Buscar sucursal ID {}", id);
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Modificar sucursal (Solo teléfono)
    @PutMapping("/{id}/telefono")
    @Operation(summary = "Actualizar teléfono", description = "Solo permite modificar el teléfono, actualiza fecha de modificación")
    public ResponseEntity<Sucursal> actualizarTelefono(
            @PathVariable String id,
            @RequestBody Map<String, String> request) { // Map simple para obtener solo el telefono

        String nuevoTelefono = request.get("phoneNumber");
        log.info("REST: Actualizar telefono ID {} a {}", id, nuevoTelefono);

        try {
            return ResponseEntity.ok(service.modificarSucursal(id, nuevoTelefono));
        } catch (RuntimeException e) {
            log.error("Error actualizando: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Crear feriados para una sucursal
    @PostMapping("/{id}/feriados")
    @Operation(summary = "Agregar feriado", description = "Añade un feriado a la lista de la sucursal")
    public ResponseEntity<Sucursal> agregarFeriado(@PathVariable String id, @Valid @RequestBody FeriadoDTO dto) {
        log.info("REST: Agregar feriado a ID {}", id);
        try {
            return ResponseEntity.ok(service.agregarFeriado(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. Eliminar feriados de una sucursal
    @DeleteMapping("/{id}/feriados/{fecha}")
    @Operation(summary = "Eliminar feriado", description = "Elimina un feriado dado por fecha (YYYY-MM-DD)")
    public ResponseEntity<Void> eliminarFeriado(@PathVariable String id, @PathVariable LocalDate fecha) {
        log.info("REST: Eliminar feriado {} de ID {}", fecha, id);
        try {
            service.eliminarFeriado(id, fecha);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 7. Obtener todos los feriados de una sucursal
    @GetMapping("/{id}/feriados")
    @Operation(summary = "Listar feriados", description = "Retorna solo la lista de feriados de la sucursal")
    public ResponseEntity<List<Feriado>> listarFeriados(@PathVariable String id) {
        log.info("REST: Listar feriados de ID {}", id);
        return service.buscarPorId(id)
                .map(s -> ResponseEntity.ok(s.getBranchHolidays()))
                .orElse(ResponseEntity.notFound().build());
    }

    // 8. Verificar si una fecha dada es o no un feriado
    @GetMapping("/{id}/es-feriado/{fecha}")
    @Operation(summary = "Verificar feriado", description = "Retorna true si la fecha es feriado, false si no")
    public ResponseEntity<Map<String, Boolean>> verificarFeriado(@PathVariable String id, @PathVariable LocalDate fecha) {
        log.info("REST: Verificar si {} es feriado en ID {}", fecha, id);
        try {
            boolean esFeriado = service.esFeriado(id, fecha);
            return ResponseEntity.ok(Map.of("esFeriado", esFeriado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}