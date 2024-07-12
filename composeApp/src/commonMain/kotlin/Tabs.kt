import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

enum class Tabs {
    Main,
    Stats,
    Settings;

    companion object{
        val state = mutableStateOf(entries[0])
    }
}