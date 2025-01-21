package com.example.web.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "board1")
data class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long = 0,

    @Column(nullable = false)
    var subject: String = "",  // 기본값 추가

    @Column(nullable = false)
    var writer: String = "",   // 기본값 추가

    @Column
    var mail: String? = null,

    @Column(nullable = false)
    var password: String = "", // 기본값 추가

    @Column(columnDefinition = "TEXT")
    var content: String = "",  // 기본값 추가

    @Column
    var hit: Int = 0,

    @Column(nullable = false)
    var wip: String = "",      // 기본값 추가

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var wdate: LocalDateTime = LocalDateTime.now(),

    @Transient
    var wgap: Int = 0
) {
    // 기본 생성자
    constructor() : this(
        seq = 0,
        subject = "",
        writer = "",
        mail = null,
        password = "",
        content = "",
        hit = 0,
        wip = "",
        wdate = LocalDateTime.now(),
        wgap = 0
    )
}