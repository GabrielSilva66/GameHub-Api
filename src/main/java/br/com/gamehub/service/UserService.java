package br.com.gamehub.service;

import br.com.gamehub.dto.request.ProfileRequestDTO;
import br.com.gamehub.dto.response.UserResponseDTO;
import br.com.gamehub.dto.request.UserRequestDTO;
import br.com.gamehub.enums.UserType;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.mapper.UserMapper;
import br.com.gamehub.mapper.UserObtainedGameMapper;
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

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        // Verifica se o e-mail ou o nome de usuário já estão em uso
        if (userRepository.findByEmail(userRequestDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        Profile profile = new Profile();
        profileRepository.save(profile);

        User user = UserMapper.toEntity(userRequestDTO, profile);
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.passwordHash()));
        user = userRepository.save(user);
        return UserMapper.toResponseDTO(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponseDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setUserType(Converter.stringToEnum(UserType.class, userRequestDTO.userType()));
        user.setEmail(userRequestDTO.email());
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.passwordHash()));
        user = userRepository.save(user);
        return UserMapper.toResponseDTO(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }


}
