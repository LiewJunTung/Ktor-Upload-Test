package org.liewjuntung.testktor

import kotlinx.cinterop.*
import platform.Foundation.*

fun NSData.string(): String? {
    return NSString.create(this, NSUTF8StringEncoding) as String?
}

fun NSData.toByteArray(): ByteArray {
    val data: CPointer<ByteVar> = bytes!!.reinterpret()
    return ByteArray(length.toInt()) { index -> data[index] }
}

fun ByteArray.toNSData(): NSData = NSMutableData().apply {
    if (isEmpty()) return@apply
    this@toNSData.usePinned {
        appendBytes(it.addressOf(0), size.convert())
    }
}

