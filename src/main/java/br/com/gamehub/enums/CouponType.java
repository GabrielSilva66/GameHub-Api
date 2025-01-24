/**
 * CouponType
 * 
 * <p>
 * Enum that represents the type of discount coupon. This can be either a fixed value discount or a percentage-based discount.
 * </p>
 * 
 * <p>
 * The two possible types are:
 * </p>
 * <ul>
 *     <li><strong>VALUE:</strong> Represents a fixed value discount (e.g., $10 off).</li>
 *     <li><strong>PERCENTAGE:</strong> Represents a percentage-based discount (e.g., 20% off).</li>
 * </ul>
 * 
 * @author Gabriel Silva
 * @since 2025-01-23
 */
package br.com.gamehub.enums;

/**
 * Enum representing the types of coupons available.
 * The coupon can either be a fixed value or a percentage discount.
 */
public enum CouponType {
    VALUE,       // Represents a fixed value discount
    PERCENTAGE   // Represents a percentage-based discount
}
