import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

enum class MainButton(val description: String) {
    AddShot("Add Shot"),
    AddEmptyShot("Add Empty Shot"),
    RemoveShot("Remove Shot"),
    RemoveEmptyShot("Remove Empty Shot"),
    SellShot("Sell Shot"),
    RemoveSellShot("Remove Sell Shot");

    fun onClick() = when(this) {
        AddShot -> States.addFullShot()
        AddEmptyShot -> States.addEmptyShot()
        RemoveShot -> States.removeFullShot()
        RemoveEmptyShot -> States.removeEmptyShot()
        SellShot -> States.addSellShot()
        RemoveSellShot -> States.removeSellShot()
    }

    fun isEnabled(): Boolean = when(this) {
        AddShot -> States.shotsBeingSold.value == 0 && States.fullShotsOnTray.value + States.emptyShotsOnTray.value < 24
        AddEmptyShot -> States.shotsBeingSold.value == 0 && States.fullShotsOnTray.value + States.emptyShotsOnTray.value < 24
        RemoveShot -> States.shotsBeingSold.value == 0 && States.fullShotsOnTray.value > 0
        RemoveEmptyShot -> States.shotsBeingSold.value == 0 && States.emptyShotsOnTray.value > 0
        SellShot -> States.fullShotsOnTray.value > 0
        RemoveSellShot -> States.shotsBeingSold.value > 0
    }

    companion object {
        private const val PADDING = 5f

        @Composable
        fun Render() {
            for (i in entries) {
                Button(
                    modifier = Modifier.padding(Dp(PADDING)),
                    enabled = i.isEnabled(),
                    onClick = { i.onClick() }
                ) {
                    Text(text = i.description)
                }
            }
        }
    }
}