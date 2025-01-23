package br.com.gamehub.mapper;

import br.com.gamehub.dto.request.DiscountCouponRequestDTO;
import br.com.gamehub.dto.response.DiscountCouponResponseDTO;
import br.com.gamehub.model.DiscountCoupon;
import br.com.gamehub.model.Store;

public class DiscountCouponMapper {
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
