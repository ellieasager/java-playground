package com.tst

import scala.collection.mutable

object CruiseWizard extends App {

  case class Rate(rateCode: String, rateGroup: String)
  case class CabinPrice(cabinCode: String,
                        rateCode: String,
                        price: BigDecimal)
  case class BestGroupPrice(cabinCode: String,
                            rateCode: String,
                            price: BigDecimal,
                            rateGroup: String)

  def getBestGroupPrices(rates: Seq[Rate],
                         prices: Seq[CabinPrice]): Seq[BestGroupPrice] = {

    // maps rateCode to rateGroup
    val rateToRateGroup = rates.map(r => r.rateCode -> r.rateGroup).toMap

    // maps "rateGroup_cabinCode" to BestGroupPrice
    val data: mutable.HashMap[String, BestGroupPrice] = mutable.HashMap.empty

    // iterate over prices and populate our data map with _lowest_ prices
    prices.foreach(p => {

      val rateGroup = rateToRateGroup.getOrElse(p.rateCode, "")
      val groupAndCabinCode = rateGroup + "_" + p.cabinCode

      data.get(groupAndCabinCode) match

        case Some(bestGroupPrice: BestGroupPrice) =>
          // we already have a price for this rateGroup_cabinCode

          if (bestGroupPrice.price > p.price)
            // found a lower price than what we've seen before!
            // store it instead of the old price
            data.addOne(groupAndCabinCode -> BestGroupPrice(p.cabinCode, p.rateCode, p.price, rateGroup))

        case None =>
          // no price recorded for this rateGroup_cabinCode so far, let's add it
          data.addOne(groupAndCabinCode -> BestGroupPrice(p.cabinCode, p.rateCode, p.price, rateGroup))
    })

    // sort it by cabinCode, rateCode and price since this is how the sample answer looks like
    data.values.toSeq.sortBy(groupPrice => (groupPrice.cabinCode, groupPrice.rateCode, groupPrice.price))
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