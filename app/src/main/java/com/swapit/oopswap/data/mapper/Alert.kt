package com.swapit.oopswap.data.mapper

import com.swapit.oopswap.data.datasource.remote.dto.response.alert.AlertResponse
import com.swapit.oopswap.domain.model.alert.Alert

fun AlertResponse.toDomain(): Alert {
    return Alert(
        notificationsId = this.notificationsId,
        type = this.type,
        title = this.title,
        body = this.body,
        relatedData = this.relatedData,
        createdAt = this.createdAt,
    )
}
