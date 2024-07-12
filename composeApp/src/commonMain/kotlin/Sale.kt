data class Sale(
    var price: Int,
    var youKeep: Int,
    var tip: Int,
    var shots: Int,
    val paymentMethod: PaymentMethod
)