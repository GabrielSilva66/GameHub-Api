/**
 * UserRepository
 *
 * <p>
 * Repository interface for handling data access for the User entity.
 * Provides methods for searching and querying users in the system.
 * </p>
 *
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email.
     *
     * <p>
     * This method retrieves the user by the provided email address.
     * </p>
     *
     * @param no_email the email address of the user to search for.
     * @return an {@link Optional} containing the found {@link User}, or an empty {@link Optional} if no user is found.
     */
    Optional<User> findByEmail(String no_email);
}
