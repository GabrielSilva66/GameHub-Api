/**
 * GameStatus
 * 
 * <p>
 * Enum that represents the possible statuses of a game in a user's gaming experience.
 * It helps track the current stage of a game in terms of progress.
 * </p>
 * 
 * <p>
 * The four possible statuses are:
 * </p>
 * <ul>
 *     <li><strong>NOT_PLAYED:</strong> Represents a game that has not been played yet.</li>
 *     <li><strong>PLAYING:</strong> Represents a game that is currently being played.</li>
 *     <li><strong>FINISHED:</strong> Represents a game that has been completed by the user.</li>
 *     <li><strong>ON_PAUSE:</strong> Represents a game that is paused and not being played.</li>
 * </ul>
 * 
 * @author Gabriel Silva
 * @since 2025-01-23
 */
package br.com.gamehub.enums;

/**
 * Enum representing the various statuses a game can have in the user's game collection.
 * It helps track the current state of the game in terms of user interaction.
 */
public enum GameStatus {
    NOT_PLAYED,  // The game has not been started yet by the user
    PLAYING,     // The user is currently playing the game
    FINISHED,    // The user has completed the game
    ON_PAUSE     // The user has paused the game
}
