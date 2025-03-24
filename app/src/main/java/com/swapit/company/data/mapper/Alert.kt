package com.swapit.company.data.mapper

import com.swapit.company.data.datasource.remote.dto.response.alert.AlertResponse
import com.swapit.company.domain.model.alert.Alert

fun AlertResponse.toDomain(): Alert{
    return Alert(
        notificationsId = this.notificationsId,
        type = this.type,
        title = this.title,
        body = this.body,
        deeplink = this.deeplink,
        createdAt = this.createdAt
    )
}