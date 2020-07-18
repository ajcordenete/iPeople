package com.aljon.module.local.features.auth.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aljon.module.domain.features.auth.models.Session

@Entity(tableName = DBSession.SESSION_TABLE_NAME)
data class DBSession(
    @PrimaryKey(autoGenerate = true)
    var pk_id: Long? = null,
    @ColumnInfo(name = "name")
    var name: String? = "",
    var email: String? = "",
    var uid: String = ""
) {

    companion object {
        const val SESSION_TABLE_NAME = "session"
        const val EMPTY_USER_ID = "empty"

        /**
         * Returns an empty user.
         */
        fun empty(): DBSession {
            return DBSession(
                name = EMPTY_USER_ID
            )
        }

        fun fromDomain(session: Session): DBSession {
            return with(session) {
                DBSession(
                    name = name,
                    email = email,
                    uid = id.orEmpty()
                )
            }
        }

        fun toDomain(dbSession: DBSession): Session {
            return with(dbSession) {
                Session(
                    email = email.orEmpty(),
                    name = name.orEmpty(),
                    id = uid
                )
            }
        }
    }
}
