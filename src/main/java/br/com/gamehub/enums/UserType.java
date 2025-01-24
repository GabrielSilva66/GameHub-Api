/**
 * UserType
 * 
 * <p>
 * Enum that represents the type of user within the system.
 * </p>
 * 
 * <p>
 * The three possible user types are:
 * </p>
 * <ul>
 *     <li><strong>ADMIN:</strong> Represents an administrator with elevated privileges.</li>
 *     <li><strong>USER:</strong> Represents a regular user with standard access.</li>
 *     <li><strong>GUEST:</strong> Represents a guest with limited access to resources.</li>
 * </ul>
 * 
 * @author Gabriel Silva
 * @since 2025-01-23
 */
package br.com.gamehub.enums;

/**
 * Enum representing different types of users in the system.
 * It is used for defining roles and access levels for users.
 */
public enum UserType {
    ADMIN,   // Represents an administrative user with full access
    COMMON,  // Represents a regular user with standard access
    GUEST    // Represents a guest user with limited access
}
