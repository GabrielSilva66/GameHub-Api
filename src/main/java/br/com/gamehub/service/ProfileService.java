/**
 * Service for managing user profiles.
 * <p>
 * This service provides methods to create, retrieve, update, and delete user profiles.
 * It interacts with the {@link ProfileRepository}.
 * </p>
 *
 * @author Gabriel Victor
 * @since 2025-01-23
 */
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

    private final ProfileRepository profileRepository; // Assume que você tenha um ProfileRepository

    @Autowired
    public ProfileService(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;  // Associa o repositório ao service
    }

    /**
     * Creates a new user profile.
     *
     * @param dto the profile request DTO containing the profile details.
     * @return the response DTO for the newly created profile.
     */
    public ProfileResponseDTO createProfile(ProfileRequestDTO dto) {
        Profile profile = ProfileMapper.toEntity(dto); // Usando o método toEntity que você forneceu
        profile = profileRepository.save(profile);
        return ProfileMapper.toResponse(profile); // Converte para DTO para retornar
    }

    /**
     * Retrieves all user profiles.
     *
     * @return a list of response DTOs for all user profiles.
     */
    public List<ProfileResponseDTO> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                .map(ProfileMapper::toResponse)
                .toList(); // Converte todos para DTOs
    }

    /**
     * Retrieves a user profile by its ID.
     *
     * @param id the ID of the user profile to retrieve.
     * @return the response DTO for the specified profile, or null if not found.
     */
    public ProfileResponseDTO getProfileById(Long id) {
        Optional<Profile> profile = profileRepository.findById(id);
        return profile.map(ProfileMapper::toResponse).orElse(null);
    }

    /**
     * Updates an existing user profile.
     *
     * @param id the ID of the profile to update.
     * @param dto the request DTO containing the updated profile details.
     * @return the response DTO for the updated profile, or null if the profile does not exist.
     */
    public ProfileResponseDTO updateProfile(Long id, ProfileRequestDTO dto) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (optionalProfile.isPresent()) {
            Profile profile = ProfileMapper.toEntity(dto);
            profile = profileRepository.save(profile);
            return ProfileMapper.toResponse(profile); // Retorna o perfil atualizado
        }
        return null; // Caso não exista um perfil com o ID fornecido
    }

    /**
     * Deletes a user profile by its ID.
     *
     * @param id the ID of the profile to delete.
     * @return true if the profile was deleted successfully, false if not found.
     */
    public boolean deleteProfile(Long id) {
        if (profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
            return true; // Deletado com sucesso
        }
        return false; // Não encontrado
    }
}
