package org.liewjuntung.testktor

import platform.Foundation.NSData
import platform.Foundation.create

actual fun readFile(fileUrl: String): ByteArray {
    val nsdata = NSData.create(contentsOfFile = fileUrl) ?: throw Exception("file not found!")
    return nsdata.toByteArray()
}
