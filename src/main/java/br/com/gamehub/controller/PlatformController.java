package br.com.gamehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gamehub.dto.request.PlatformRequestDTO;
import br.com.gamehub.dto.response.PlatformResponseDTO;
import br.com.gamehub.service.PlatformService;
import jakarta.validation.Valid;

public class PlatformController {
   private final PlatformService platformService;

   @Autowired
   public PlatformController(PlatformService platformService) {
      this.platformService = platformService;
   }

   @PostMapping
   public ResponseEntity<PlatformResponseDTO> createPlatform(
         @Valid @RequestBody PlatformRequestDTO platformRequestDTO) {
      PlatformResponseDTO platformResponseDTO = platformService.createPlatform(platformRequestDTO);

      return new ResponseEntity<>(platformResponseDTO, HttpStatus.CREATED);
   }

   @GetMapping("/{id}")
   public ResponseEntity<PlatformResponseDTO> getPlatformById(@PathVariable("id") Long id) {
      PlatformResponseDTO platformResponseDTO = platformService.getPlatformById(id);

      return new ResponseEntity<>(platformResponseDTO, HttpStatus.OK);

   }

   @GetMapping
   public ResponseEntity<List<PlatformResponseDTO>> getAllPlatforms() {
      List<PlatformResponseDTO> platformResponseDTOs = platformService.getAllPlatforms();

      return new ResponseEntity<>(platformResponseDTOs, HttpStatus.OK);
   }

   @PutMapping("/{id}")
   public ResponseEntity<PlatformResponseDTO> updatePlatform(@PathVariable("id") Long id,
         @Valid @RequestBody PlatformRequestDTO platformRequestDTO) {
      PlatformResponseDTO platformResponseDTO = platformService.updatePlatform(id, platformRequestDTO);

      return new ResponseEntity<>(platformResponseDTO, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletePlatform(@PathVariable("id") Long id) {
      platformService.deletePlatform(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/search")
   public ResponseEntity<Page<PlatformResponseDTO>> searchPlatforms(
         @RequestParam(defaultValue = "") String name,
         @RequestParam(defaultValue = "0") Integer page,
         @RequestParam(defaultValue = "10") Integer size,
         @RequestParam(defaultValue = "id_platform") String orderBy,
         @RequestParam(defaultValue = "asc") String direction) {
      Page<PlatformResponseDTO> platformResponseDTOs = platformService.searchPlatforms(name, page, size, orderBy,
            direction);

      return new ResponseEntity<>(platformResponseDTOs, HttpStatus.OK);
   }
}
