package prettys

import java.io.File
import java.util.Date
import javax.imageio.ImageIO
import java.text.SimpleDateFormat
import org.rogach.scallop._
import org.apache.commons.io.FileUtils

////////////////////////////////////////////////////////////////////////////////

object Prettifiers {
  def isDivider(line: String): Boolean = {
    // A single line
//    asserty(!line.contains("\n"))
    assert(!line.contains("\n"))

    val stripped = line.dropWhile(_ == ' ').reverse.dropWhile(_ == ' ').reverse
    val onlySlashes = stripped.count(_ == '/') == stripped.size

    onlySlashes && stripped.size >= 10
  }

  def cleanDivider(line: String): String =
    if (isDivider(line)) {
      // This is a divider.
      val lineLength = 80
      val numLeadingSpaces = line.takeWhile(_ == ' ').size
      " " * numLeadingSpaces + "/" * (lineLength - numLeadingSpaces)
    } else {
      line
    }

  //////////////////////////////////////////////////////////////////////////////

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
}
