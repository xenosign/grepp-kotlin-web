package com.example.web.to

data class BoardTO(
    var seq: String = "",
    var subject: String = "",
    var writer: String = "",
    var mail: String = "",
    var password: String = "",
    var content: String = "",
    var hit: String = "",
    var wip: String = "",
    var wdate: String = "",
    var wgap: Int = 0
)