package com.loadtester.util.helper

import org.apache.commons.codec.digest.DigestUtils
import scala.util.Try

object StringHelper {
  val KANA_ZEN = Set[Char](
      'ア','イ','ウ','エ','オ',
      'カ','キ','ク','ケ','コ',
      'サ','シ','ス','セ','ソ',
      'タ','チ','ツ','テ','ト',
      'ナ','ニ','ヌ','ネ','ノ',
      'ハ','ヒ','フ','ヘ','ホ',
      'マ','ミ','ム','メ','モ',
      'ヤ','ユ','ヨ',
      'ラ','リ','ル','レ','ロ',
      'ワ','ヰ','ヱ',
      'ヲ','ン',
      'ァ','ィ','ゥ','ェ','ォ', 'ッ',
      'ャ','ュ','ョ','ヮ','ヵ', 'ヶ',
      'ー',
      'ヴ',
      'ガ','ギ','グ','ゲ','ゴ',
      'ザ','ジ','ズ','ゼ','ゾ',
      'ダ','ヂ','ヅ','デ','ド',
      'バ','ビ','ブ','ベ','ボ',
      'パ','ピ','プ','ペ','ポ', '　'
      )
  val TELNUM_CHARS = Set[Char]('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-')
  val TELNUM_MIN_CHARS = Set[Char]('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
  val REG_TELNUM = "([0-9](\\-)?)+[0-9]".r
  private val alphArr = {
    val alphSeq = {'a' to 'z'}
    alphSeq.toArray
  }

  def isTelNum(str:String) = {
    str match {
      case REG_TELNUM(_*) => true
      case _ => false
    }
  }

  def toMinimumTelNum(str:String) = {
    str.filter(TELNUM_MIN_CHARS.contains(_))
  }

  def toReadableTelNum(str:String) = {
    def splitNand4(str:String, n:Int) = str.take(n) + "-" + str.drop(n).take(4) + "-" + str.drop(n + 4)
    def splitNand3(str:String, n:Int) = str.take(n) + "-" + str.drop(n).take(3) + "-" + str.drop(n + 3)
    
    if(str.startsWith("0120")) splitNand3(str, 4)
    else if(str.startsWith("090") | str.startsWith("080") | str.startsWith("070") | str.startsWith("060")) splitNand4(str, 3)
    else if(str.startsWith("03")) splitNand4(str, 2)
    else splitNand4(str, 4)
  }

  def alphIndex(index:Int):String = {
    val rep = (index / 26)+1
    val c = alphArr(index % alphArr.size)
    (1 to rep).map(i => c.toString).mkString
  }

  def isKana(str:String) = str.forall(c => {KANA_ZEN.contains(c)})

  def hashOf(pass:String):String = {
    val hex = DigestUtils.sha256Hex(pass)
    hex
  }

  def quoteWith(str:String, q:String):String = q + str + q

  def makeCsvString(lines:List[String]*):String = {
    lines.map(_.mkString(",")).mkString("\n")
  }
  def filterSpaces(str:String):String = str.filterNot(c => c ==' ' || c == '\t')

  def parseIntOption(str:String):Option[Int] = {
    str match {
      case "" => None
      case s => Try(str.toInt).toOption
    }
  }
  def parseFloatOption(str:String):Option[Float] = {
    str match {
      case "" => None
      case s => Try(str.toFloat).toOption
    }
  }
}
