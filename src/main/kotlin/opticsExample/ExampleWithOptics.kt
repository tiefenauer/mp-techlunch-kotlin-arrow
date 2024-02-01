package opticsExample

import arrow.optics.Lens

object ExampleWithOptics {

    // InsurantPolicy > Insurant
    private val insurantLens: Lens<InsurancePolicy, Insurant> = Lens(
        get = { it.insurant },
        set = { insurancePolicy, insurant -> insurancePolicy.copy(insurant = insurant) }
    )
    // Insurant > Address
    private val addressLens: Lens<Insurant, Address> = Lens(
        get = { it.address },
        set = { insurant, address -> insurant.copy(address = address) }
    )
    // Address > streetNumber
    private val streetNumberLens: Lens<Address, String> = Lens(
        get = { it.streetNumber },
        set = { address, streetNumber -> address.copy(streetNumber = streetNumber) }
    )
    // all together now
    val insurancePolicyInsurantAddressStreetNumberLens = insurantLens
        .compose(addressLens)
        .compose(streetNumberLens)

    @JvmStatic
    fun main(args: Array<String>) {
        println("old street number: ${policy.insurant.address.streetNumber}")
        // the old fashioned way, which requires us to create/compose lenses programmatically
        var updatedPolicy = insurancePolicyInsurantAddressStreetNumberLens.modify(policy) { "43" }
        println("new street number: ${updatedPolicy.insurant.address.streetNumber}")

        // the better way, using generated lens code:
        updatedPolicy = InsurancePolicy.insurant.address.streetNumber.modify(policy) { "43" }
        println("new street number: ${updatedPolicy.insurant.address.streetNumber}")
    }
}