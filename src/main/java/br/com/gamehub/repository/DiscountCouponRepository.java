package br.com.gamehub.repository;

import br.com.gamehub.model.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Long> {
}
