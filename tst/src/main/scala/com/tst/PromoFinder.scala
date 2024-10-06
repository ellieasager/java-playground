package com.tst

import scala.collection.immutable

object PromoFinder extends App {

  def allCombinablePromotions(allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {

    PromotionData(allPromotions).findLongestCombos()
  }

  def combinablePromotions( promotionCode: String,
                            allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {

    // let's use our previously written method
    allCombinablePromotions(allPromotions)
      // grab only related promo combos
      .filter(promoCombo => promoCombo.promotionCodes.contains(promotionCode))
      // order promo codes in the correct order - the requested promo code first
      .map(promoCombo => promoCombo.putRequestedPromoCodeFirst(promotionCode))
  }

}


/*
Promotion(P1, Seq(P3)) // P1 is not combinable with P3
Promotion(P2, Seq(P4, P5)) // P2 is not combinable with P4 and P5
Promotion(P3, Seq(P1)) // P3 is not combinable with P1
Promotion(P4, Seq(P2)) // P4 is not combinable with P2
Promotion(P5, Seq(P2)) // P5 is not combinable with P2

All:
Seq(
PromotionCombo(Seq(P1, P2)),
PromotionCombo(Seq(P1, P4, P5)),
PromotionCombo(Seq(P2, P3)),
PromotionCombo(Seq(P3, P4, P5))
)

P1:
Seq(
PromotionCombo(Seq(P1, P2)),
PromotionCombo(Seq(P1, P4, P5))
)
P3:
Seq(
PromotionCombo(Seq(P3, P2)),
PromotionCombo(Seq(P3, P4, P5))
)
 */