package com.tst

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.boundary
import scala.util.boundary.break

case class Promotion(code: String, notCombinableWith: Seq[String])

case class PromotionCombo(promotionCodes: Seq[String]) {

  def putRequestedPromoCodeFirst(targetPromoCode: String): PromotionCombo = {
    val promoCodesArray = ArrayBuffer[String]().addAll(promotionCodes)
    val idxOfTargetCode = promoCodesArray.indexOf(targetPromoCode)

    if (promotionCodes.size <= 1 || idxOfTargetCode == 0)
      return this // nothing needs to be changed
    else {
      // classic 2-element swap
      val originalFirstCode = promoCodesArray(0)
      promoCodesArray(0) = promoCodesArray(idxOfTargetCode)
      promoCodesArray(idxOfTargetCode) = originalFirstCode
    }
    PromotionCombo(promoCodesArray.toSeq)
  }
}

class PromotionData(val illegalCombos: Seq[Promotion]) {

  private val existingPromos: Seq[String] = illegalCombos.map(p => p.code)
  private val legalCombos = mutable.Set[Set[String]]()

  def findLongestCombos(): Seq[PromotionCombo] = {
    findLegalCodeCombos(existingPromos.toSet)
    removeProperSubsets()
    sortLegalCombos()
  }

  private def findLegalCodeCombos(potentialCombo: Set[String]): Unit = {

    if (potentialCombo.size == 2) {
      val promo1 = potentialCombo.head
      val promo2 = potentialCombo.last
      if (isValidPair(promo1, promo2))
        legalCombos +=potentialCombo
    }
    // recursion (size > 2)
    else if (potentialCombo.size > 2) {
      getFirstInvalidPair(potentialCombo) match {
        case Some(invalidPair) =>
          findLegalCodeCombos(potentialCombo - invalidPair._1)
          findLegalCodeCombos(potentialCombo - invalidPair._2)
        case None =>
          legalCombos +=potentialCombo
      }
    }

    // if the size of a potential promotion combo is 1 or 0,
    // there could be no invalid combination, so we do nothing and exit this method for good
  }

  private def isValidPair(promo1: String, promo2: String): Boolean = {
    !illegalCombos.exists(promo =>
      promo.code == promo1 && promo.notCombinableWith.contains(promo2) ||
        promo.code == promo1 && promo.notCombinableWith.contains(promo2))
  }

  private def getFirstInvalidPair(potentialCombo: Set[String]): Option[(String, String)] = {

    val indexedCombo = potentialCombo.toIndexedSeq
    boundary:
      indexedCombo.zipWithIndex.foreach {
        case (promo, idx) => getFirstConflictingCodeForThisCode(promo, indexedCombo.slice(idx + 1, indexedCombo.length)) match {
          case Some(conflictingPromo) =>
            break(Some((promo, conflictingPromo)))
          case None =>
        }
      }
      None
  }

  private def getFirstConflictingCodeForThisCode(promo: String, otherPromos: IndexedSeq[String]): Option[String] = {
    boundary:
      if (otherPromos.isEmpty)
        return None
      else {
        otherPromos.foreach(
          otherPromo =>
            if !isValidPair(promo, otherPromo) then break(Some(otherPromo)))
      }
      None
  }

  private def removeProperSubsets(): Unit = {

    // STEP1: sort all valid promo combos by length and store in an List
    var allCombos = List[mutable.Set[Set[String]]]()
    // initialize to empty Sets in any way I can - don't want to get stuck here, need to revisit this
    for (_ <- 0 to existingPromos.size) {
      allCombos = allCombos :+ mutable.Set[Set[String]]()
    }

    // `allCombos` is an array where at position [i] we want to have a set of combos that have `i` number of promo codes
    // obviously, `allCombos[0]` and `allCombos[1]` should be empty
    // the longest combo size can theoretically equal to existingPromos.size
    legalCombos.foreach {
      combo => {
        allCombos(combo.size).addOne(combo)
      }
    }

    // STEP2: remove redundant data (proper subsets that are stored along with their supersets)
    // HOW: divide `allCombos` into 2 "zones":
    // - `largestCombos`: the longest combos in our working dataset that are NOT a subset of other combos and
    // - `combosToCheck`: sets of smaller combos that MAY be subsets of longer combos
    // Examine each large combo against smaller sized combos and remove the subsets.
    // Iterate, each time removing the set of longest combos from our working dataset and, therefore,
    // shrinking `combosToCheck` size.

    // if `allCombos.size` is 0 or 1, it means that we either have no promo combinations or
    // that the longest "combination" is of size 1 - not really a valid combination;
    // let's start at size 2 at least
    if (allCombos.size >= 2) {

      while (allCombos.nonEmpty) {
        val largestCombos = allCombos.last
        val combosToCheck = allCombos.slice(0, allCombos.size - 1).flatten

        largestCombos.foreach(
          // examine each longest combo and see if there are some subsets of it out there
          // if found - remove them legalCombos and allCombos
          largeCombo => removeSubsets(largeCombo, combosToCheck)
        )
        allCombos = allCombos.dropRight(1)
      }
    }
  }

  private def removeSubsets(superset: Set[String], possibleSubsets: List[Set[String]]): Unit = {
      val onlyUniqueCombos = mutable.Set[Set[String]]()
      possibleSubsets.foreach(combo =>
        if (combo.subsetOf(superset))
          legalCombos -= combo
        else
          onlyUniqueCombos.addOne(combo)
      )
  }

  private def sortLegalCombos(): Seq[PromotionCombo] = {
    legalCombos
      .map(combo => PromotionCombo(combo.toSeq.sorted)).toSeq
      .sortBy(promotionCombo => promotionCombo.promotionCodes.head)
  }
}