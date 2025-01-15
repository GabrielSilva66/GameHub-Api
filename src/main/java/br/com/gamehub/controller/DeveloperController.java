package br.com.gamehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamehub.dto.request.DeveloperRequestDTO;
import br.com.gamehub.dto.response.DeveloperResponseDTO;
import br.com.gamehub.service.DeveloperService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/developers")
@CrossOrigin
public class DeveloperController {
   private final DeveloperService developerService;

   @Autowired
   public DeveloperController(DeveloperService developerService) {
      this.developerService = developerService;
   }

   @PostMapping
   public ResponseEntity<DeveloperResponseDTO> createDeveloper(
         @Valid @RequestBody DeveloperRequestDTO developerRequestDTO) {
      DeveloperResponseDTO developerResponseDTO = developerService.createDeveloper(developerRequestDTO);

      return new ResponseEntity<>(developerResponseDTO, HttpStatus.CREATED);
   }

   @GetMapping("/{id}")
   public ResponseEntity<DeveloperResponseDTO> getDeveloperById(@PathVariable("id") Long id) {
      DeveloperResponseDTO developerResponseDTO = developerService.getDeveloperById(id);

      return new ResponseEntity<>(developerResponseDTO, HttpStatus.OK);

   }

   @GetMapping
   public ResponseEntity<List<DeveloperResponseDTO>> getAllDevelopers() {
      List<DeveloperResponseDTO> developerResponseDTOs = developerService.getAllDevelopers();

      return new ResponseEntity<>(developerResponseDTOs, HttpStatus.OK);
   }

   @PutMapping("/{id}")
   public ResponseEntity<DeveloperResponseDTO> updateDeveloper(@PathVariable("id") Long id,
         @Valid @RequestBody DeveloperRequestDTO developerRequestDTO) {
      DeveloperResponseDTO developerResponseDTO = developerService.updateDeveloper(id, developerRequestDTO);

      return new ResponseEntity<>(developerResponseDTO, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteDeveloper(@PathVariable("id") Long id) {
      developerService.deleteDeveloper(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/search")
   public ResponseEntity<Page<DeveloperResponseDTO>> searchDevelopers(
         @RequestParam(defaultValue = "") String name,
         @RequestParam(defaultValue = "0") Integer page,
         @RequestParam(defaultValue = "10") Integer size,
         @RequestParam(defaultValue = "id_developer") String orderBy,
         @RequestParam(defaultValue = "asc") String direction) {
      Page<DeveloperResponseDTO> developers = developerService.searchDevelopers(name, page, size, orderBy, direction);

      return new ResponseEntity<>(developers, HttpStatus.OK);
   }
}
