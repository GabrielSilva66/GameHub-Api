package br.com.gamehub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.gamehub.dto.request.PlatformRequestDTO;
import br.com.gamehub.dto.response.PlatformResponseDTO;
import br.com.gamehub.mapper.PlatformMapper;
import br.com.gamehub.model.Platform;
import br.com.gamehub.repository.PlatformRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PlatformService {
   private final PlatformRepository platformRepository;

   @Autowired
   public PlatformService(PlatformRepository platformRepository) {
      this.platformRepository = platformRepository;
   }

   public PlatformResponseDTO createPlatform(PlatformRequestDTO platformRequestDTO) {
      Platform platform = PlatformMapper.toEntity(platformRequestDTO);
      platform = platformRepository.save(platform);

      return PlatformMapper.toResponse(platform);
   }

   public PlatformResponseDTO getPlatformById(Long id) {
      Platform platform = platformRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Platform with id " + id + " not found"));

      return PlatformMapper.toResponse(platform);
   }

   public List<PlatformResponseDTO> getAllPlatforms() {
      List<Platform> platforms = platformRepository.findAll();
      return platforms.stream().map(PlatformMapper::toResponse).toList();
   }

   public PlatformResponseDTO updatePlatform(Long id, PlatformRequestDTO platformRequestDTO) {
      Platform platform = platformRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Platform with id " + id + " not found"));

      platform.setName(platformRequestDTO.name());

      platform = platformRepository.save(platform);

      return PlatformMapper.toResponse(platform);
   }

   public void deletePlatform(Long id) {
      platformRepository.deleteById(id);
   }

   public Page<PlatformResponseDTO> searchPlatforms(String name,
         Integer pageNumber, Integer pageSize, String orderBy, String direction) {
      Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
      Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

      Page<Platform> platforms = platformRepository.search(name, pageable);
      return platforms.map(PlatformMapper::toResponse);
   }
}
