package org.liewjuntung.testktor

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import org.liewjuntung.testdb.KtDb


actual val dbDriver: SqlDriver
    get() = NativeSqliteDriver(KtDb.Schema, "verifyt.db")
