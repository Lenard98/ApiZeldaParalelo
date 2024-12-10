import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apizeldaparalelo.R

val zelda = FontFamily(
    Font(R.font.zelda) // Asegúrate de usar el nombre correcto del archivo .ttf
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent() {

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        title = {

            Text(
                text = "Zelda ",
                style = TextStyle(
                    fontFamily = zelda,
                    fontSize = 24.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        )
    )
}
