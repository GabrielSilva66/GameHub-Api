/**
 * Service for managing game assessments.
 * <p>
 * This service provides methods to create, retrieve, update, and delete assessments for games.
 * It interacts with the {@link AssessmentRepository}, {@link UserRepository}, and {@link GameRepository}.
 * </p>
 *
 * @author Gabriel Victor
 * @since 2025-01-23
 */
package br.com.gamehub.service;

import br.com.gamehub.dto.request.AssessmentRequestDTO;
import br.com.gamehub.dto.response.AssessmentResponseDTO;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.mapper.AssessmentMapper;
import br.com.gamehub.model.Assessment;
import br.com.gamehub.model.Game;
import br.com.gamehub.model.User;
import br.com.gamehub.repository.AssessmentRepository;
import br.com.gamehub.repository.GameRepository;
import br.com.gamehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.assessmentRepository = assessmentRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Creates a new game assessment for a user.
     *
     * @param userId the ID of the user creating the assessment.
     * @param gameId the ID of the game being assessed.
     * @param requestDTO the request DTO containing the assessment details.
     * @return the response DTO for the newly created assessment.
     * @throws IllegalArgumentException if the user has already assessed the game.
     * @throws EntityNotFoundException if the user or game is not found.
     */
    public AssessmentResponseDTO createAssessment(Long userId, Long gameId, AssessmentRequestDTO requestDTO) {
        // Verifica se o usuário e o jogo existem
        User user = findUserById(userId);
        Game game = findGameById(gameId);

        // Verifica se o usuário já avaliou o jogo
        assessmentRepository.findByUserIdAndGameId(userId, gameId).ifPresent(assessment -> {
            throw new IllegalArgumentException("User with id " + userId + " has already assessed the game with id " + gameId);
        });

        // Atualiza a avaliação do jogo de forma ponderada
        int newTotalEvaluation = game.getTotalEvaluation() + 1;
        double newRating = (game.getRating() * game.getTotalEvaluation() + requestDTO.rating()) / newTotalEvaluation;

        game.setRating(newRating);
        game.setTotalEvaluation(newTotalEvaluation);

        // Mapeia o DTO para a entidade e salva no banco
        Assessment assessment = AssessmentMapper.toEntity(requestDTO, user, game);
        Assessment savedAssessment = assessmentRepository.save(assessment);

        System.out.println("comentario.............................. " + assessment.getComment());
        // Retorna o DTO de resposta
        return AssessmentMapper.toResponse(savedAssessment);
    }

    /**
     * Retrieves all assessments for a specific game.
     *
     * @param gameId the ID of the game for which assessments are being retrieved.
     * @return a list of response DTOs for all assessments related to the specified game.
     * @throws EntityNotFoundException if no assessments are found for the game.
     */
    public List<AssessmentResponseDTO> getAssessmentsByGame(Long gameId) {
        List<Assessment> assessments = assessmentRepository.findAllByGameId(gameId);

        if (assessments.isEmpty()) {
            throw new EntityNotFoundException("No assessments found for game with id: " + gameId);
        }

        return assessments.stream()
                .map(AssessmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all assessments for a specific user.
     *
     * @param userId the ID of the user for which assessments are being retrieved.
     * @return a list of response DTOs for all assessments made by the specified user.
     * @throws EntityNotFoundException if no assessments are found for the user.
     */
    public List<AssessmentResponseDTO> getAssessmentsByUser(Long userId) {
        List<Assessment> assessments = assessmentRepository.findAllByUserId(userId);

        if (assessments.isEmpty()) {
            throw new EntityNotFoundException("No assessments found for user with id: " + userId);
        }

        return assessments.stream()
                .map(AssessmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing assessment for a user and game.
     *
     * @param userId the ID of the user updating the assessment.
     * @param gameId the ID of the game being updated.
     * @param requestDTO the request DTO containing the updated assessment details.
     * @return the response DTO for the updated assessment.
     * @throws EntityNotFoundException if the user or game is not found.
     * @throws IllegalArgumentException if the user has not assessed the game yet.
     */
    public AssessmentResponseDTO updateAssessment(Long userId, Long gameId, AssessmentRequestDTO requestDTO) {
        // Verifica se o usuário e o jogo existem
        Game game = findGameById(gameId);

        // Verifica se o usuário ainda não avaliou o jogo
        Assessment existingAssessment = assessmentRepository.findByUserIdAndGameId(userId, gameId).orElseThrow(() ->
                new IllegalArgumentException("User with id " + userId + " has not assessed the game with id " + gameId)
        );

        // Ajusta a avaliação do jogo para remover a contribuição da avaliação antiga
        double totalEvaluation = game.getTotalEvaluation();
        double newRating = ((game.getRating() * totalEvaluation) - existingAssessment.getRating() + requestDTO.rating()) / totalEvaluation;

        game.setRating(newRating);

        // Atualiza a avaliação existente com os novos dados
        existingAssessment.setRating(requestDTO.rating());
        existingAssessment.setComment(requestDTO.comment());

        // Salva as alterações no banco
        assessmentRepository.save(existingAssessment);

        // Retorna o DTO de resposta
        return AssessmentMapper.toResponse(existingAssessment);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user to find.
     * @return the User object.
     * @throws EntityNotFoundException if no user is found with the given ID.
     */
    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Finds a game by its ID.
     *
     * @param id the ID of the game to find.
     * @return the Game object.
     * @throws EntityNotFoundException if no game is found with the given ID.
     */
    private Game findGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));
    }
}
