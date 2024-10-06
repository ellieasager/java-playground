package com.tst

import com.tst.CruiseWizard.{BestGroupPrice, CabinPrice, Rate}
import org.scalatest.matchers.must.Matchers.contain
import org.scalatest.matchers.should.Matchers.should

class CruiseWizardTest extends org.scalatest.funsuite.AnyFunSuite {
  test("CruiseWizard.getBestGroupPrices") {
    println("starting")

    // -------- INPUT ---------------------

    val rates = List(
      Rate("M1", "Military"),
      Rate("M2", "Military"),
      Rate("S1", "Senior"),
      Rate("S2", "Senior"),
    )

    val cabinPrices = List(
      CabinPrice("CA", "M1", 200),
      CabinPrice("CA", "M2", 250),
      CabinPrice("CA", "S1", 225),
      CabinPrice("CA", "S2", 260),
      CabinPrice("CB", "M1", 230),
      CabinPrice("CB", "M2", 260),
      CabinPrice("CB", "S1", 245),
      CabinPrice("CB", "S2", 270),
    )

    // -------- END OF INPUT ---------------------
    // -------- EXPECTED OUTPUT ------------------

    val bestGroupPrices = List(
      BestGroupPrice("CA", "M1", 200.00, "Military"),
      BestGroupPrice("CA", "S1", 225.00, "Senior"),
      BestGroupPrice("CB", "M1", 230.00, "Military"),
      BestGroupPrice("CB", "S1", 245.00, "Senior"),
    )

    // -------- END OF EXPECTED OUTPUT -----------

    val results: Seq[BestGroupPrice] = CruiseWizard.getBestGroupPrices(rates, cabinPrices)
    results should contain theSameElementsInOrderAs bestGroupPrices


    println("done")
  }
}

/*
Input - Rates:
Rate(M1, Military)
Rate(M2, Military)
Rate(S1, Senior)
Rate(S2, Senior)

Input - Cabin Prices:
CabinPrice(CA, M1, 200.00)
CabinPrice(CA, M2, 250.00)
CabinPrice(CA, S1, 225.00)
CabinPrice(CA, S2, 260.00)
CabinPrice(CB, M1, 230.00)
CabinPrice(CB, M2, 260.00)
CabinPrice(CB, S1, 245.00)
CabinPrice(CB, S2, 270.00)

Expected Output - Best Cabin Prices:
BestGroupPrice(CA, M1, 200.00, Military)
BestGroupPrice(CA, S1, 225.00, Senior)
BestGroupPrice(CB, M1, 230.00, Military)
BestGroupPrice(CB, S1, 245.00, Senior)
 */
