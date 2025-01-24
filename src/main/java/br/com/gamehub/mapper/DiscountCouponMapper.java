/**
 * DiscountCouponMapper
 * 
 * <p>
 * Utility class for mapping between {@link DiscountCoupon} entity, {@link DiscountCouponRequestDTO} and {@link DiscountCouponResponseDTO}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.DiscountCouponRequestDTO;
import br.com.gamehub.dto.response.DiscountCouponResponseDTO;
import br.com.gamehub.model.DiscountCoupon;
import br.com.gamehub.model.Store;

public class DiscountCouponMapper {

   /**
    * Converts a {@link DiscountCouponRequestDTO} and a {@link Store} to a
    * {@link DiscountCoupon} entity.
    * 
    * @param discountCouponRequestDTO The DTO to be converted.
    * @param store                    The associated {@link Store} for the coupon.
    * @return The corresponding {@link DiscountCoupon} entity.
    */
   public static DiscountCoupon toEntity(DiscountCouponRequestDTO discountCouponRequestDTO, Store store) {
      if (discountCouponRequestDTO == null) {
         return null;
      }

      return DiscountCoupon.builder()
            .store(store)
            .name(discountCouponRequestDTO.name())
            .couponType(discountCouponRequestDTO.couponType())
            .value(discountCouponRequestDTO.value())
            .initialDate(discountCouponRequestDTO.initialDate())
            .deadline(discountCouponRequestDTO.deadline())
            .minPriceToUse(discountCouponRequestDTO.minPriceToUse())
            .build();
   }

   /**
    * Converts a {@link DiscountCoupon} entity to a
    * {@link DiscountCouponResponseDTO}.
    * 
    * @param discountCoupon The {@link DiscountCoupon} entity to be converted.
    * @return The corresponding {@link DiscountCouponResponseDTO}.
    */
   public static DiscountCouponResponseDTO toResponse(DiscountCoupon discountCoupon) {
      if (discountCoupon == null) {
         return null;
      }

      return new DiscountCouponResponseDTO(
            discountCoupon.getIdDiscountCoupon(),
            discountCoupon.getName(),
            StoreMapper.toResponse(discountCoupon.getStore()),
            discountCoupon.getCouponType(),
            discountCoupon.getValue(),
            discountCoupon.getInitialDate(),
            discountCoupon.getDeadline(),
            discountCoupon.getMinPriceToUse());
   }
}
