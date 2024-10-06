package com.tst

import org.scalatest.matchers.must.Matchers.contain
import org.scalatest.matchers.should.Matchers.should

class PromoFinderTest extends org.scalatest.funsuite.AnyFunSuite {
  test("PromoFinder.allCombinablePromotions") {
    println("starting")

    // -------- INPUT ---------------------

    val allPromos = List(
      Promotion("P1", Seq("P3")),
      Promotion("P2", Seq("P4", "P5")),
      Promotion("P3", Seq("P1")),
      Promotion("P4", Seq("P2")),
      Promotion("P5", Seq("P2")),
    )

    // -------- END OF INPUT ---------------------
    // -------- EXPECTED OUTPUT ------------------

    val combinablePromos = List(
      PromotionCombo(Seq("P1", "P2")),
      PromotionCombo(Seq("P1", "P4", "P5")),
      PromotionCombo(Seq("P2", "P3")),
      PromotionCombo(Seq("P3", "P4", "P5")),
    )

    // -------- END OF EXPECTED OUTPUT -----------

    PromoFinder.allCombinablePromotions(allPromos) should contain theSameElementsInOrderAs combinablePromos
    println("done")
  }

  test("PromoFinder.combinablePromotions") {
    println("starting")
    // -------- INPUT ---------------------

    val allPromos = List(
      Promotion("P1", Seq("P3")),
      Promotion("P2", Seq("P4", "P5")),
      Promotion("P3", Seq("P1")),
      Promotion("P4", Seq("P2")),
      Promotion("P5", Seq("P2")),
    )

    val combinablePromosForP1 = List(
      PromotionCombo(Seq("P1", "P2")),
      PromotionCombo(Seq("P1", "P4", "P5")),
    )
    val combinablePromosForP3 = List(
      PromotionCombo(Seq("P3", "P2")),
      PromotionCombo(Seq("P3", "P4", "P5")),
    )

    // -------- END OF INPUT ---------------------

    PromoFinder.combinablePromotions("P1", allPromos) should contain theSameElementsInOrderAs combinablePromosForP1
    PromoFinder.combinablePromotions("P3", allPromos) should contain theSameElementsInOrderAs combinablePromosForP3

    println("done")
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