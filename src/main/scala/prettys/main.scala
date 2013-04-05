package prettys

import java.io.File
import java.util.Date
import javax.imageio.ImageIO
import java.text.SimpleDateFormat
import org.rogach.scallop._
import org.apache.commons.io.FileUtils
//import nebula._

////////////////////////////////////////////////////////////////////////////////

class Conf(args: Seq[String]) extends ScallopConf(args) {
  banner("PrettyS: A tool to reformat Scala source code to make it a bit prettier, according to my (Eric Christiansen's) completely arbitrary tastes.")

  val inputScala = opt[String](
    "inputScala",
    descr = "The Scala code you want made pretty.",
    required = true) map /*ExistingFile.apply*/ { path =>
      val file = new File(path)
      assert(file.isFile)
      assert(file.exists)
      file
    }

  val outputScala = opt[String](
    "outputScala",
    descr = "Where to write the pretty Scala source.",
    required = false) map (x => new File(x))

  val clobberInput = toggle(
    "clobberInput",
    default = Some(false))
}

object Main {
  def main(unparsedArgs: Array[String]) {
    val args = new Conf(unparsedArgs)
    println(args.summary)

    ////////////////////////////////////////////////////////////////////////////

    val source = FileUtils.readFileToString(args.inputScala())
    val pretty = Prettifiers.cleanDividers(source)

    // If the output isn't specified
    val outputFile: File = if (args.clobberInput()) {
      args.inputScala()
    } else if (args.outputScala.isDefined) {
      args.outputScala()
    } else ???

    FileUtils.writeStringToFile(outputFile, pretty)
  }
}