package com.aljon.module.local.features.auth.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.aljon.module.domain.features.auth.models.User

@Entity(tableName = DBUser.USER_TABLE_NAME, indices = [Index(value = ["email"], unique = true)])
data class DBUser(
    @PrimaryKey(autoGenerate = true)
    var pk_id: Long? = null,
    @ColumnInfo(name = "name")
    var name: String? = "",
    @ColumnInfo(name = "email")
    var email: String? = "",
    @ColumnInfo(name = "password")
    val password: String? = "",
    @ColumnInfo(name = "country")
    var country: String? = ""
) {

    companion object {
        const val USER_TABLE_NAME = "user"

        fun toDomain(dbUser: DBUser): User {
            return with(dbUser) {
                User(
                    id = pk_id,
                    name = name,
                    email = email,
                    password = password,
                    country = country
                )
            }
        }

        fun fromDomain(user: User): DBUser {
            return with(user) {
                DBUser(
                    name = name,
                    email = email,
                    password = password,
                    country = country
                )
            }
        }
    }
}
