import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.shoppingapp.Screen
import com.example.shoppingapp.viewModel.CartViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OrderNowScreen(cartViewModel: CartViewModel, navController: NavController) {
    // State variables for address fields
    var flatNumber by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var paymentMode by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showConfirmation by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Example payment modes
    val paymentModes = listOf("Credit Card", "Debit Card", "PayPal")

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        // Heading for the Order Now Screen
        Text(
            text = "Order",
            style = MaterialTheme.typography.h4,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Address fields
        Text("Enter your address", style = MaterialTheme.typography.h6, color = Color.White)

        // Flat Number Input
        TextField(
            value = flatNumber,
            onValueChange = { flatNumber = it },
            placeholder = { Text("Flat No") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                placeholderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Area Input
        TextField(
            value = area,
            onValueChange = { area = it },
            placeholder = { Text("Area") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                placeholderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // City Input
        TextField(
            value = city,
            onValueChange = { city = it },
            placeholder = { Text("City") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                placeholderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // State Input
        TextField(
            value = state,
            onValueChange = { state = it },
            placeholder = { Text("State") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                placeholderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Pincode Input
        TextField(
            value = pincode,
            onValueChange = { pincode = it },
            placeholder = { Text("Pincode") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                placeholderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // Payment mode selection
        Text("Select Payment Mode", style = MaterialTheme.typography.h6, color = Color.White)
        Box {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (paymentMode.isEmpty()) "Select Payment Mode" else paymentMode,
                    color = Color.White
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                paymentModes.forEach { mode ->
                    DropdownMenuItem(
                        onClick = {
                            paymentMode = mode
                            expanded = false // Close the dropdown after selection
                        }
                    ) {
                        Text(mode, color = Color.Black)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Place Order button
        Button(
            onClick = {
                // Show confirmation message
                showConfirmation = true

                // Delay and navigate to HomeScreen
                coroutineScope.launch {
                    delay(3000) // Show the message for 2 seconds
                    navController.navigate(Screen.Home.route) // Replace "home_screen" with your actual HomeScreen route
                }

                cartViewModel.clearCart()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Place Order", color = Color.White)
        }

        // Show confirmation dialog or Snackbar
        if (showConfirmation) {
            AlertDialog(
                onDismissRequest = { /* Handle dialog dismissal */ },
                title = { Text("Order Placed", color = Color.White) },
                text = { Text("Thank you for shopping!", color = Color.White) },
                buttons = {
                    Button(
                        onClick = { showConfirmation = false },
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Text("OK", color = Color.White)
                    }
                },
                backgroundColor = Color.DarkGray
            )
        }
    }
}
