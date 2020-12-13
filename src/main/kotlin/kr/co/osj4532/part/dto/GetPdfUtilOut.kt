package kr.co.osj4532.part.dto

/**
 * 201213 | osj4532 | created
 */

data class GetPdfUtilOut (
    val fileName: String,
    var pdf: ByteArray = byteArrayOf()
)