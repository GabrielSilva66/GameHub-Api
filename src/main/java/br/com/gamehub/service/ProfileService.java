package br.com.gamehub.service;

import br.com.gamehub.dto.request.ProfileRequestDTO;
import br.com.gamehub.dto.response.ProfileResponseDTO;
import br.com.gamehub.mapper.ProfileMapper;
import br.com.gamehub.model.Profile;
import br.com.gamehub.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private  final ProfileRepository profileRepository; // Assume que você tenha um ProfileRepository

    @Autowired
    public ProfileService(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;  // Associa o repositório ao service
    }
    
    

    // Criar novo perfil
    public ProfileResponseDTO createProfile(ProfileRequestDTO dto) {
        Profile profile = ProfileMapper.toEntity(dto); // Usando o método toEntity que você forneceu
        profile = profileRepository.save(profile);
        return ProfileMapper.toResponseDTO(profile); // Converte para DTO para retornar
    }

    // Obter todos os perfis
    public List<ProfileResponseDTO> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                .map(ProfileMapper::toResponseDTO)
                .toList(); // Converte todos para DTOs
    }

    // Obter perfil por ID
    public ProfileResponseDTO getProfileById(Long id) {
        Optional<Profile> profile = profileRepository.findById(id);
        return profile.map(ProfileMapper::toResponseDTO).orElse(null);
    }

    // Atualizar perfil existente
    public ProfileResponseDTO updateProfile(Long id, ProfileRequestDTO dto) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (optionalProfile.isPresent()) {
            Profile profile = ProfileMapper.toEntity(dto);
            profile = profileRepository.save(profile);
            return ProfileMapper.toResponseDTO(profile); // Retorna o perfil atualizado
        }
        return null; // Caso não exista um perfil com o ID fornecido
    }

    // Excluir perfil por ID
    public boolean deleteProfile(Long id) {
        if (profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
            return true; // Deletado com sucesso
        }
        return false; // Não encontrado
    }
}
