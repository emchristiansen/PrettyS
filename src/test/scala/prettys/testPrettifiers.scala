package prettys

import org.scalatest._
import org.scalatest.prop._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import nebula._

////////////////////////////////////////////////////////////////////////////////

@RunWith(classOf[JUnitRunner])
class TestPrettifiers extends FunSuite {
  object Common {
    val source =
      "  ////////////////// "    
  }
  
  test("isDivider") {
    import Common._
    
    asserty(Prettifiers.isDivider(source))
  }
  
  test("clean Divider on line with whitespace") {
    import Common._
    
    val golden = "  " + "/" * 78
    val output = Prettifiers.cleanDivider(source)
    
    asserty(golden == output)
  }
}