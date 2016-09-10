package workman.api.json.response

case class UserAuthResp(
    success:Boolean,
    token:String,
    email:String,
    name:String
    )
