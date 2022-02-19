package kr.co.today

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


// 버튼 여러번 입력 방지 시간
const val BUTTON_SKIP_DURATION = 1500L

//궁금톡 서버 호출 갯수
const val SIZE_OPEN_TALK = 1000
const val SIZE_MIN_SHOW = 10

val TODAY: ZonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())

const val REPLY_RELOAD = 3000
const val DISMISS_CODE = 4000