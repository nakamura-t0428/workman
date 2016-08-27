package com.loadtester.util.helper

object CollectionHelper {
  /**
   * リストを特定の中さNに調整する
   * 
   * 長すぎる場合は先頭からN要素に切り詰める。
   * 短すぎる場合は要素Zで埋める。
   */
  def resizeList[A](lst:List[A], size:Int, z:A):List[A] = {
    if(lst.size < size) lst:::List.fill(size - lst.size)(z)
    else lst.take(size)
  }
}