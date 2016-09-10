package workman.api.json.response

import workman.data.model.User
import workman.data.dto.MemberInfo

object MemberInfoResp {
  def fromModel(u:User) = MemberInfoResp(
      u.userId,
      u.name
      )
  def fromModel(u:MemberInfo) = MemberInfoResp(
      u.userId,
      u.name
      )
}
case class MemberInfoResp(userId:String, name:String)
