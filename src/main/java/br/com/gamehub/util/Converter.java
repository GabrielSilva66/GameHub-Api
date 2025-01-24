/**
 * Utility class for converting strings to enum values.
 * <p>
 * This class provides a generic method to convert a string value to the corresponding enum constant.
 * The string is case-insensitive and will be converted to uppercase before being matched.
 * If the string cannot be mapped to an enum value, an exception will be thrown.
 * </p>
 *
 * @author Gabriel Victor
 * @since 2025-01-23
 */
package br.com.gamehub.util;

public class Converter {

    /**
     * Converts a string to an enum value of the specified enum type.
     *
     * @param <T> the type of enum to convert to.
     * @param enumType the class type of the enum.
     * @param value the string value to be converted to an enum constant.
     * @return the enum constant corresponding to the string value.
     * @throws IllegalArgumentException if the value is null, empty, or does not match any enum constant.
     */
    public static <T extends Enum<T>> T stringToEnum(Class<T> enumType, String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty");
        }
        try {
            return Enum.valueOf(enumType, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for enum " + enumType.getSimpleName() + ": " + value, e);
        }
    }

}
