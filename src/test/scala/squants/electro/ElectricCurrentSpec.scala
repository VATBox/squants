/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import org.scalatest.{ Matchers, FlatSpec }
import squants.MetricSystem
import squants.time.Seconds
import squants.energy.Watts
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ElectricCurrentSpec extends FlatSpec with Matchers {

  behavior of "ElectricalCurrent and its Units of Measure"

  it should "create values using UOM factories" in {
    Amperes(1).toAmperes should be(1)
    Milliamperes(1).toMilliamperes should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Amperes(1)
    x.toAmperes should be(1.0)
    x.toMilliamperes should be(1 / MetricSystem.Milli)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Amperes(1).toString(Amperes) should be("1.0 A")
    Milliamperes(1).toString(Milliamperes) should be("1.0 mA")
  }

  it should "return ElectricalPotential when multiplied by ElectricalResistance" in {
    Amperes(1) * Ohms(1) should be(Volts(1))
  }

  it should "return Energy when multiplied by ElectricalPotential" in {
    Amperes(1) * Volts(1) should be(Watts(1))
  }

  it should "return ElectricalConductance when divided by ElectricalPotential" in {
    Amperes(1) / Volts(1) should be(Siemens(1))
  }

  it should "return ElectricalCharge when multiplied by Time" in {
    Amperes(1) * Seconds(1) should be(Coulombs(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Amperes(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[ElectricCurrent](ser)
    x should be(des)
  }

  behavior of "ElectricalCurrentConversions"

  it should "provide aliases for single unit values" in {
    import ElectricCurrentConversions._

    ampere should be(Amperes(1))
    amp should be(Amperes(1))
    milliampere should be(Milliamperes(1))
    milliamp should be(Milliamperes(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricCurrentConversions._

    val d = 10d
    d.amperes should be(Amperes(d))
    d.amps should be(Amperes(d))
    d.A should be(Amperes(d))
    d.milliampers should be(Milliamperes(d))
    d.milliamps should be(Milliamperes(d))
    d.mA should be(Milliamperes(d))
  }
}
