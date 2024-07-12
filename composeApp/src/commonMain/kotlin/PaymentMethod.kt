import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.Runnable

enum class PaymentMethod(val description: String, toYou: Boolean) {
    Cash("Cash", true),
    Tab("Tab", false),
    Wire("Wire", true);

    companion object {
        val padding = 5f

        @Composable
        fun Render() {
            for (i in entries) {
                Button(modifier = Modifier.padding(Dp(padding)), onClick = {
                    States.sellShots(i)
                }) {
                    Text(text = "Pay ${i.description}")
                }
            }
        }
    }
}