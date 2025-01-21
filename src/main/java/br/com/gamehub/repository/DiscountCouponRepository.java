package br.com.gamehub.repository;

import br.com.gamehub.model.DiscountCoupon;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Long> {
   @NativeQuery("SELECT * FROM GH_DISCOUNT_COUPON WHERE no_name ILIKE %?1%")
   Page<DiscountCoupon> search(String name, Pageable pageable);

   @NativeQuery("SELECT * FROM GH_DISCOUNT_COUPON WHERE dt_initial <= now() AND dt_deadline > now()")
   List<DiscountCoupon> findActiveCoupons();
}
