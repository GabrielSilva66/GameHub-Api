/**
 * StoreGameId
 * 
 * <p>
 * Represents a composite key for the StoreGame entity, consisting of the store ID and game ID. 
 * This class is used to uniquely identify a StoreGame relationship.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.id;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class StoreGameId implements Serializable {
   private Long storeId;
   private Long gameId;
}
