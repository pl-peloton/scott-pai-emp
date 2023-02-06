/*
https://developer.android.com/codelabs/basic-android-kotlin-compose-kotlin-fundamentals-practice-problems#2
*/

fun main() {
    val child = 5
    val adult = 28
    val senior = 87

    val isMonday = true

    println("The movie ticket price for a person aged $child is  \$${ticketPrice(child, isMonday)}.")
    println("The movie ticket price for a person aged $adult is \$${ticketPrice(adult, isMonday)}.")
    println("The movie ticket price for a person aged $senior is \$${ticketPrice(senior, isMonday)}.")
}

fun ticketPrice(age: Int, dayOfWeek: Boolean): Int {
    if (age <= 12) {
        return 15
    } else if (age <= 60) {
        return 30
    } else if (age > 60) {
        return 20
    } else {
        return -1
    }
}