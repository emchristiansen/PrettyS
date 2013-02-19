package prettys

import java.io.File
import java.util.Date
import javax.imageio.ImageIO
import java.text.SimpleDateFormat
import org.rogach.scallop._
import org.apache.commons.io.FileUtils
import nebula._

///////////////////////////////////////////////////////////

class Conf(args: Seq[String]) extends ScallopConf(args) {
  banner("PrettyS: A tool to reformat Scala source code to make it a bit prettier, according to my (Eric Christiansen's) completely arbitrary tastes.")

  val inputScala = opt[String](
    "inputScala",
    descr = "The Scala code you want made pretty.",
    required = true) map ExistingFile.apply

  val outputScala = opt[String](
    "outputScala",
    descr = "Where to write the pretty Scala source.",
    required = false)
}

object Main {
  def cleanDivider(line: String): String = {
    val stripped = line.stripLineEnd.reverse.stripLineEnd.reverse
    val onlySlashes = stripped.count(_ == '/') == stripped.size
    if (onlySlashes && stripped.size >= 10) {
      // This is a divider.
      val lineLength = 80
      val numLeadingSpaces = line.takeWhile(_ == ' ').size
      " " * numLeadingSpaces + "/" * (lineLength - numLeadingSpaces)
    } else {
      line
    }
  }

  /**
   * Considers every line consisting of only "/" characters (at least 10)
   * to be a divider in the code: something to signal the reader the code
   * topic is changing. The line can have spaces on either side and still
   * be considered a divider.
   *
   * Returns source with cleaned-up dividers.
   */
  def cleanDividers(in: String): String = {
    val lines = in.split("\n") map cleanDivider
    lines.mkString("\n")
  }

  def main(unparsedArgs: Array[String]) {
    val args = new Conf(unparsedArgs)
    println(args.summary)

    val source = FileUtils.readFileToString(args.inputScala())
    val pretty = cleanDividers(source)
    
    println(pretty)
  }
}
