import androidx.compose.runtime.mutableStateOf

object States {
    val fullShotsOnTray = mutableStateOf(0)
    val emptyShotsOnTray = mutableStateOf(0)
    val moneyOnTray = mutableStateOf(0)
    val shotsSold = mutableStateOf(0)

    fun addFullShot() {
        fullShotsOnTray.value++
    }
    fun addEmptyShot() {
        emptyShotsOnTray.value++
    }
    fun removeFullShot() {
        fullShotsOnTray.value--
        if (fullShotsOnTray.value < 0) {
            fullShotsOnTray.value = 0
        }
    }
    fun removeEmptyShot() {
        emptyShotsOnTray.value--
        if (emptyShotsOnTray.value < 0) {
            emptyShotsOnTray.value = 0
        }
    }
}