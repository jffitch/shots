import androidx.compose.runtime.mutableStateOf

object States {
    val fullShotsOnTray = mutableStateOf(0)
    val emptyShotsOnTray = mutableStateOf(0)
    val moneyOnTray = mutableStateOf(0)
    val shotsBeingSold = mutableStateOf(0)
    val sales = mutableListOf<Sale>()

    fun addFullShot() {
        fullShotsOnTray.value++
    }
    fun addEmptyShot() {
        emptyShotsOnTray.value++
    }
    fun removeFullShot() {
        fullShotsOnTray.value--
    }
    fun removeEmptyShot() {
        emptyShotsOnTray.value--
    }
    fun addSellShot() {
        fullShotsOnTray.value--
        shotsBeingSold.value++
    }
    fun removeSellShot() {
        shotsBeingSold.value--
        fullShotsOnTray.value++
    }
    fun sellShots(paymentMethod: PaymentMethod) {
        sales.add(Sale(
            price = Setting.Price.state.value * shotsBeingSold.value,
            youKeep = Setting.YouKeep.state.value * shotsBeingSold.value,
            tip = 0,
            shots = shotsBeingSold.value,
            paymentMethod = paymentMethod,
        ))
        emptyShotsOnTray.value += shotsBeingSold.value
        shotsBeingSold.value = 0
        updateMoney()
    }

    fun undoSale() {
        sales.removeLastOrNull()
        updateMoney()
    }

    fun updateMoney() {
        var money = Setting.StartingCash.state.value
        for (sale in sales) {
            if (sale.paymentMethod == PaymentMethod.Cash) {
                money += sale.price + sale.tip
            }
        }
    }
}