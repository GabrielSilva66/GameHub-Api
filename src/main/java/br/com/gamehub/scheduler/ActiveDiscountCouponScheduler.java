package br.com.gamehub.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.gamehub.service.ActiveDiscountCouponService;

@Component
public class ActiveDiscountCouponScheduler {
   private final ActiveDiscountCouponService activeDiscountCouponService;

   @Autowired
   public ActiveDiscountCouponScheduler(ActiveDiscountCouponService activeDiscountCouponService) {
      this.activeDiscountCouponService = activeDiscountCouponService;
   }

   @Scheduled(fixedRate = 60000)
   public void updateActiveDiscountCoupons() {
      activeDiscountCouponService.updateActiveDiscountCoupons();
   }
}
