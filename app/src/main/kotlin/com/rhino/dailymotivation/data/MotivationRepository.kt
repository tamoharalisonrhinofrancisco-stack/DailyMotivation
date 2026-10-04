package com.rhino.dailymotivation.data

class MotivationRepository {
    private val messages = Motivations.messages

    fun getTodayMessage(dateKey: String): String {
        if (messages.isEmpty()) return "Do not measure your journey by the distance remaining. Measure it by the strength you have already gained."
        val index = (dateKey.hashCode().coerceAtLeast(0) % messages.size)
        return messages[index.coerceAtMost(messages.size - 1)]
    }

    fun getRandomMessage(exclude: String): String {
        if (messages.size == 1) return messages.first()
        var candidate = messages.random()
        while (candidate == exclude && messages.size > 1) {
            candidate = messages.random()
        }
        return candidate
    }
}
