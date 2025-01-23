/**
 * ActiveDiscountCouponScheduler
 *
 * <p>
 * Scheduler component responsible for periodically updating active discount coupons.
 * This class is triggered at fixed intervals to ensure that active discount coupons are updated regularly.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.gamehub.service.ActiveDiscountCouponService;

@Component
public class ActiveDiscountCouponScheduler {

   private final ActiveDiscountCouponService activeDiscountCouponService;

   /**
    * Constructor for ActiveDiscountCouponScheduler.
    *
    * @param activeDiscountCouponService the service responsible for updating
    *                                    active discount coupons.
    */
   @Autowired
   public ActiveDiscountCouponScheduler(ActiveDiscountCouponService activeDiscountCouponService) {
      this.activeDiscountCouponService = activeDiscountCouponService;
   }

   /**
    * Scheduled method that updates active discount coupons at a fixed interval.
    *
    * <p>
    * This method runs every 60 seconds (60000 milliseconds), calling the
    * {@link ActiveDiscountCouponService#updateActiveDiscountCoupons()} method to
    * ensure the active discount coupons are up-to-date.
    * </p>
    */
   @Scheduled(fixedRate = 60000)
   public void updateActiveDiscountCoupons() {
      activeDiscountCouponService.updateActiveDiscountCoupons();
   }
}
