package com.loadtester.data.dto

case class Limit(
    limit:Int,
    page:Int
    )

object DefaultLimit extends Limit(10, 0)
