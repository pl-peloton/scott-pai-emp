//  https://developer.android.com/codelabs/basic-android-kotlin-compose-kotlin-fundamentals-practice-problems#1

fun main() {
    val morningNotification = 51
    val eveningNotification = 135

    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)
}

fun printNotificationSummary(numberOfMessages: Int) {
    if (numberOfMessages < 99) {
        println("You have ${numberOfMessages} notifications.")
    } else {
        println("Your phone is blowing up! You have ${numberOfMessages} notifications")
    }
}

