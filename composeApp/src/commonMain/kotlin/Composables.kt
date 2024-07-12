import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shots.composeapp.generated.resources.Res
import shots.composeapp.generated.resources.empty_shot_glass
import shots.composeapp.generated.resources.full_shot_glass
import shots.composeapp.generated.resources.shot_tray

@Composable
@Preview
fun App() {
    Column {
        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.Red, RectangleShape)) {
            Box(modifier = Modifier.align(Alignment.Center).matchParentSize()) {
                RenderTab()
            }
        }
        TabLayout(5)
    }
}

@Composable
fun TabLayout(padding: Int = 0) {
    Row {
        for (tab in Tabs.entries) {
            Button(onClick = { Tabs.state.value = tab}, Modifier.weight(1f).padding(all = Dp(padding.toFloat()))) {
                Text(tab.name)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BoxScope.RenderMain() {
    Image(
        painter = painterResource(Res.drawable.shot_tray),
        contentDescription = "Shot Tray",
        alignment = Alignment.Center,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxSize()
    )
    Text(
        text = "$${States.moneyOnTray.value}",
        modifier = Modifier.align(Alignment.Center).offset(x = Dp(0f), y = Dp(-50f)),
        fontSize = TextUnit(25f, TextUnitType.Sp),
        fontWeight = FontWeight.Bold
    )
    DisplayShots(States.fullShotsOnTray.value, States.emptyShotsOnTray.value)
    FlowRow(
        modifier = Modifier.align(Alignment.BottomStart)
    ) {
        MainButton.Render()
    }
    if (States.shotsBeingSold.value != 0) {
        Column (modifier = Modifier.align(Alignment.TopStart).offset(x = Dp(5f), y = Dp(5f))) {
            Row {
                Text(
                    text = "$${States.shotsBeingSold.value * Setting.Price.state.value}",
                    fontSize = TextUnit(40f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
                FlowRow {
                    for (i in 1..States.shotsBeingSold.value) {
                        Image(
                            painter = painterResource(Res.drawable.full_shot_glass),
                            contentDescription = "Full Shot Glass",
                            modifier = Modifier.scale(2f).padding(Dp(10f)).align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            FlowRow {
                PaymentMethod.Render()
            }
        }
    }
}

@Composable
fun BoxScope.RenderTab() {
    when (Tabs.state.value) {
        Tabs.Main -> RenderMain()
        Tabs.Stats -> RenderStats()
        Tabs.Settings -> Setting.Render()
    }
}

@Composable
fun BoxScope.DisplayShots(full: Int, empty: Int) {
    val padding = Dp(10f)
    val scale = 2f
    for (i in 0 until full) {
        Image(
            painter = painterResource(Res.drawable.full_shot_glass),
            contentDescription = "Full Shot Glass",
            modifier = modifierOffset(i).scale(scale).padding(padding).align(Alignment.Center)
        )
    }
    for (i in full until empty + full) {
        Image(
            painter = painterResource(Res.drawable.empty_shot_glass),
            contentDescription = "Empty Shot Glass",
            modifier = modifierOffset(i).scale(scale).padding(padding).align(Alignment.Center)
        )
    }
}

fun modifierOffset(i: Int): Modifier {
    val offsetPositions = listOf(
        Pair(-60, -40),
        Pair(60, -40),
        Pair(-120, -15),
        Pair(120, -15),
        Pair(-90, -25),
        Pair(90, -25),
        Pair(-30, 10),
        Pair(30, 10),
        Pair(-120, 10),
        Pair(120, 10),
        Pair(-60, -20),
        Pair(60, -20),
        Pair(0, 15),
        Pair(-90, 5),
        Pair(90, 5),
        Pair(0, 35),
        Pair(-60, 5),
        Pair(60, 5),
        Pair(-30, 30),
        Pair(30, 30),
        Pair(-60, 30),
        Pair(60, 30),
        Pair(-90, 25),
        Pair(90, 25)
    )
    if (i < offsetPositions.size) {
        return Modifier.offset(
            x = Dp(offsetPositions[i].first.toFloat()),
            y = Dp(offsetPositions[i].second.toFloat())
        )
    }
    return Modifier
}

@Composable
fun BoxScope.RenderStats() {
    val saleStatList = PaymentMethod.entries.map { Sale(0, 0, 0, 0, it) }
    for (sale in States.sales) {
        val saleStat = saleStatList.first {it.paymentMethod == sale.paymentMethod}
        saleStat.price += sale.price
        saleStat.youKeep += sale.youKeep
        saleStat.tip += sale.tip
        saleStat.shots += sale.shots
    }
    Column {
        for (stat in saleStatList) {
            Text(text = "$${stat.price} total collected using ${stat.paymentMethod.description}")
            Text(text = "$${stat.youKeep} total base earnings using ${stat.paymentMethod.description}")
            Text(text = "$${stat.tip} total tips using ${stat.paymentMethod.description}")
            Text(text = "$${stat.youKeep + stat.tip} total earnings using ${stat.paymentMethod.description}")
        }
    }
}
