/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.photo

import org.scalatest.{ Matchers, FlatSpec }
import scala.language.postfixOps
import squants.time.Seconds
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class LuminousEnergySpec extends FlatSpec with Matchers {

  behavior of "LuminousEnergy and its Units of Measure"

  it should "create values using UOM factories" in {
    LumenSeconds(1).toLumenSeconds should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = LumenSeconds(1)
    x.toLumenSeconds should be(1)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    LumenSeconds(1).toString(LumenSeconds) should be("1.0 lm⋅s")
  }

  it should "return LuminousFlux when divided by Time" in {
    LumenSeconds(1) / Seconds(1) should be(Lumens(1))
  }

  it should "return Time when divided by LuminousFlux" in {
    LumenSeconds(1) / Lumens(1) should be(Seconds(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = LumenSeconds(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[LuminousEnergy](ser)
    x should be(des)
  }

  behavior of "LuminousEnergyConversions"

  it should "provide aliases for single unit values" in {
    import LuminousEnergyConversions._

    lumenSecond should be(LumenSeconds(1))
  }

  it should "provide implicit conversion from Double" in {
    import LuminousEnergyConversions._

    val d = 10d
    d.lumenSeconds should be(LumenSeconds(d))
  }
}
