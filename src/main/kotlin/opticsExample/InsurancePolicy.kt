package opticsExample

import arrow.optics.optics

@optics
data class InsurancePolicy(val policyNumber: String, val insurant: Insurant, val vehicle: Vehicle) {
    companion object
}

@optics
data class Insurant(val firstName: String, val lastName: String, val address: Address) {
    companion object
}

@optics
data class Vehicle(val make: String, val model: String, val horsePower: Int) {
    companion object
}

@optics
data class Address(val street: String, val streetNumber: String, val zip: String, val city: String) {
    companion object
}

// an immutable InsurancePolicy
val policy = InsurancePolicy(
    policyNumber = "123456789",
    insurant = Insurant(
        firstName = "John",
        lastName = "Doe",
        address = Address(
            street = "Musterstrasse",
            streetNumber = "42",
            city = "Musterhausen",
            zip = "1234",
        )
    ),
    vehicle = Vehicle(
        make = "Audi",
        model = "A3",
        horsePower = 115
    )
)