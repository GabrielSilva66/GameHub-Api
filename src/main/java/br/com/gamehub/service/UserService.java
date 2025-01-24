/**
 * Service for managing users in the system.
 * <p>
 * This service provides methods for creating, updating, retrieving, and deleting users.
 * It also handles user-specific operations such as password hashing and validation of email and username uniqueness.
 * </p>
 *
 * @author Gabriel Victor
 * @since 2025-01-23
 */
package br.com.gamehub.service;

import br.com.gamehub.dto.request.ProfileRequestDTO;
import br.com.gamehub.dto.response.UserResponseDTO;
import br.com.gamehub.dto.request.UserRequestDTO;
import br.com.gamehub.enums.UserType;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.mapper.UserMapper;
import br.com.gamehub.model.Profile;
import br.com.gamehub.model.User;
import br.com.gamehub.repository.ProfileRepository;
import br.com.gamehub.repository.UserRepository;
import br.com.gamehub.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user in the system.
     *
     * @param userRequestDTO the user data transfer object containing the details of the new user.
     * @return a response DTO for the newly created user.
     * @throws IllegalArgumentException if the email is already in use.
     */
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        // Verifies if the email is already in use
        if (userRepository.findByEmail(userRequestDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Creates a new profile and saves it
        Profile profile = new Profile();
        profileRepository.save(profile);

        // Converts the request DTO to an entity, hashes the password, and saves the user
        User user = UserMapper.toEntity(userRequestDTO, profile);
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.passwordHash()));
        user = userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a list of response DTOs for all users.
     */
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return the response DTO for the user.
     * @throws EntityNotFoundException if the user is not found.
     */
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return UserMapper.toResponse(user);
    }

    /**
     * Updates the information of an existing user.
     *
     * @param id the ID of the user to update.
     * @param userRequestDTO the data transfer object containing the updated user details.
     * @return the response DTO for the updated user.
     * @throws EntityNotFoundException if the user is not found.
     */
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Updates the user's information
        user.setUserType(Converter.stringToEnum(UserType.class, userRequestDTO.userType()));
        user.setEmail(userRequestDTO.email());
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.passwordHash()));
        user = userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    /**
     * Deletes a user from the system.
     *
     * @param id the ID of the user to delete.
     * @throws EntityNotFoundException if the user is not found.
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
