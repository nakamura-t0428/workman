package com.loadtester.api.json.response

case class ProjectDetailResp(
    prjInfo:ProjectInfoResponse,
    owner:MemberInfoResp,
    members:List[MemberInfoResp]
    )
