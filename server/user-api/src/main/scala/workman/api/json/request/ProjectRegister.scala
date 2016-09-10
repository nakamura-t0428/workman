package workman.api.json.request

case class ProjectRegister(
    name:String,
    description:String,
    compId:String) {
  require(!name.isEmpty())
  require(!compId.isEmpty())
}
