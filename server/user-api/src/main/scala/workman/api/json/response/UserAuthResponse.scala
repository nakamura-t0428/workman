package workman.api.json.response

case class UserAuthResponse(
    success:Boolean,
    token:String,
    email:String,
    name:String
    )
