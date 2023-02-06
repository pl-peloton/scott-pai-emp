// https://developer.android.com/codelabs/basic-android-kotlin-compose-kotlin-fundamentals-practice-problems#3

fun main() {
    var celcius = 27.0
    val kelvin = 350.0
    val farenheit = 10.0

    printFinalTemperature(celcius, "Celcius","Farenheit", ::celciusToFarenheit)
    printFinalTemperature(kelvin, "Kelvin", "Celcius", ::kelvinToCelcius)
    printFinalTemperature(farenheit, "Farenheit", "Kelvin", ::farenheitToKelvin)
}

fun celciusToFarenheit(celciusTemp: Double): Double {
    return (celciusTemp * (9.0/5.0) + 32.0)
}

fun kelvinToCelcius(ktemp: Double):Double {
    return ktemp - 273.15
}

fun farenheitToKelvin(ftemp: Double):Double {
    return (5.0/9.0) * (ftemp - 32.0) + 273.15
}

fun printFinalTemperature(
    initialMeasurement: Double,
    initialUnit: String,
    finalUnit: String,
    conversionFormula: (Double) -> Double
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}