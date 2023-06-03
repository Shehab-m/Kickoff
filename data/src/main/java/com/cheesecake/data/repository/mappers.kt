//package com.cheesecake.data.repository
//
//import com.cheesecake.data.local.models.TeamLocalDto
//
//fun TeamLocalDto.toLocalDto(userId: Int): Team {
//
//    if (messageFrom?.guid == userId) {
//        return Chat(
//            fullName = messageTo?.fullname?.substringBefore("##") ?: "",
//            guid = messageTo?.guid ?: 0,
//            icon = messageTo?.icon?.larger ?: "",
//            time = time ?: 0,
//            recentMessage = message ?: ""
//        )
//    } else {
//        return Chat(
//            fullName = messageFrom?.fullname?.substringBefore("##") ?: "",
//            guid = messageFrom?.guid ?: 0,
//            icon = messageFrom?.icon?.larger ?: "",
//            time = time ?: 0,
//            recentMessage = message ?: ""
//        )
//    }
//}
//
//fun ConversationDTO.toEntity(userId: Int) : Chats {
//    return Chats(
//        chats = this.list?.map{it.toLocalDto(userId)} ?: emptyList(),
//        count = this.count?:0,
//    )
//}
//fun ChatLocalDto.toEntity(): Chat {
//    return Chat(
//        fullName = fullName,
//        guid = guid,
//        icon = icon,
//        time = time,
//        recentMessage = recentMessage
//    )
//}
//
//fun Chat.toLocalDto(): ChatLocalDto {
//    return ChatLocalDto(
//        fullName = fullName,
//        guid = guid,
//        icon = icon,
//        time = time,
//        recentMessage = recentMessage
//    )
//}