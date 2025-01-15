package br.com.gamehub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gamehub.dto.request.DeveloperRequestDTO;
import br.com.gamehub.dto.response.DeveloperResponseDTO;
import br.com.gamehub.mapper.DeveloperMapper;
import br.com.gamehub.model.Developer;
import br.com.gamehub.repository.DeveloperRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DeveloperService {
   private final DeveloperRepository developerRepository;

   @Autowired
   public DeveloperService(DeveloperRepository developerRepository) {
      this.developerRepository = developerRepository;
   }

   public DeveloperResponseDTO createDeveloper(DeveloperRequestDTO developerRequestDTO) {
      Developer developer = DeveloperMapper.toEntity(developerRequestDTO);
      developer = developerRepository.save(developer);

      return DeveloperMapper.toResponse(developer);
   }

   public DeveloperResponseDTO getDeveloperById(Long id) {
      Developer developer = developerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + id + " not found"));

      return DeveloperMapper.toResponse(developer);
   }

   public List<DeveloperResponseDTO> getAllDevelopers() {
      List<Developer> developers = developerRepository.findAll();
      return developers.stream().map(DeveloperMapper::toResponse).toList();
   }

   public DeveloperResponseDTO updateDeveloper(Long id, DeveloperRequestDTO developerRequestDTO) {
      Developer developer = developerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + id + " not found"));

      developer.setName(developerRequestDTO.name());
      developer.setYearOfFoundation(developerRequestDTO.yearOfFoundation());
      developer.setHostCountry(developerRequestDTO.hostCountry());

      developer = developerRepository.save(developer);

      return DeveloperMapper.toResponse(developer);
   }

   public void deleteDeveloper(Long id) {
      Developer developer = developerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + id + " not found"));

      developerRepository.delete(developer);
   }
}
