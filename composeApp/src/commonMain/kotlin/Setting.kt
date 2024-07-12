import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import shots.composeapp.generated.resources.Res
import shots.composeapp.generated.resources.ic_arrow_down
import shots.composeapp.generated.resources.ic_arrow_up

enum class Setting (
    val displayName: String,
    val state: MutableState<Int>,
    val minValue: Int = 0,
    val maxValue: Int? = null,
    val minConstraint: String? = null,
    val maxConstraint: String? = null
) {
    Price(
        displayName = "Price",
        state = mutableStateOf(3),
        minValue = 1,
        minConstraint = "You Keep"
    ),
    YouKeep(
        displayName = "You Keep",
        state = mutableStateOf(1),
        maxConstraint = "Price"
    ),
    StartingCash(
        displayName = "Starting Cash",
        state = mutableStateOf(0)
    );

    fun add() {
        if (maxValue?.let { state.value >= it } == true) {
            return
        }
        entries.firstOrNull { it.displayName == maxConstraint }?.let {
            if (state.value >= it.state.value) {
                return
            }
        }
        state.value++
    }

    fun subtract() {
        if (state.value <= minValue) {
            return
        }
        entries.firstOrNull { it.displayName == minConstraint }?.let {
            if (state.value <= it.state.value) {
                return
            }
        }
        state.value--
    }

    companion object {
        @Composable
        fun Render() {
            Column {
                for (setting in entries) {
                    Row {
                        Text(
                            text = "${setting.displayName}: $${setting.state.value}",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Image(
                            painter = painterResource(Res.drawable.ic_arrow_up),
                            contentDescription = "Up Arrow",
                            modifier = Modifier.clickable {
                                setting.add()
                            }
                        )
                        Image(
                            painter = painterResource(Res.drawable.ic_arrow_down),
                            contentDescription = "Down Arrow",
                            modifier = Modifier.clickable {
                                setting.subtract()
                            }
                        )
                    }
                }
            }
        }
    }
}