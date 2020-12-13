package kr.co.osj4532.kr.co.osj4532.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * 201213 | osj4532 | created
 */

class DateUtils {
    companion object {
        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("YYYYMMddHHmmss")
            val date = Date()
            return sdf.format(date)
        }
    }
}