package br.com.gamehub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import br.com.gamehub.model.ActiveDiscountCoupon;

public interface ActiveDiscountCouponRepository extends JpaRepository<ActiveDiscountCoupon, Long> {
   @NativeQuery("SELECT ADC.* FROM GH_ACTIVE_DISCOUNT_COUPON ADC LEFT JOIN GH_DISCOUNT_COUPON DC ON ADC.id_discount_coupon = DC.id_discount_coupon WHERE DC.no_name ILIKE %:name%")
   Page<ActiveDiscountCoupon> search(@Param("name") String name, Pageable pageable);
}
