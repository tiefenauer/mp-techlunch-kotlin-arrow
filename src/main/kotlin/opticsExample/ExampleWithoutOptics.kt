package opticsExample


object ExampleWithoutOptics {
    @JvmStatic
    fun main(args: Array<String>) {
        println("old street number: ${policy.insurant.address.streetNumber}")
        val updatedPolicy = policy.copy(
            insurant = policy.insurant.copy(
                address = policy.insurant.address.copy(
                    streetNumber = "43"
                )
            )
        )
        println("new street number: ${updatedPolicy.insurant.address.streetNumber}")
    }
}