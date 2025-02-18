package com.example.shoppinglisttesting.ui.screens

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shoppinglisttesting.ui.NavigationItem
import com.example.shoppinglisttesting.ui.viewmodels.MainViewmodel
import com.google.gson.Gson
import timber.log.Timber


@Composable
fun AddShoppingItemScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewmodel: MainViewmodel
) {

    val shoppingItem by viewmodel.shoppingItem.collectAsStateWithLifecycle()
    Timber.d("DATA : %s", Gson().toJson(shoppingItem))
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(shoppingItem.image.ifBlank { R.drawable.ic_search_category_default })
                    .placeholder(R.drawable.ic_search_category_default) // This should work correctly
                    .build(),
                contentDescription = "Shopping Item Image",
                modifier = Modifier
                    .size(70.dp)
                    .clickable {
                        navController.navigate(NavigationItem.ImageSelection.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
            )

            OutlinedTextField(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 10.dp),
                value = shoppingItem.name,
                onValueChange = { value ->
                    viewmodel.updateShoppingItem(shoppingItem.copy(name = value))
                },
                placeholder = {
                    Text(text = "Name")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                value = if (shoppingItem.amount == 0) "" else shoppingItem.amount.toString(),
                onValueChange = { value ->
                    viewmodel.updateShoppingItem(
                        shoppingItem.copy(
                            amount = value.toIntOrNull() ?: 0
                        )
                    )
                },
                placeholder = {
                    Text(text = "Amount")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                value = if (shoppingItem.pricePerItem == 0.0) "" else shoppingItem.pricePerItem.toString(),
                onValueChange = { value ->
                    viewmodel.updateShoppingItem(
                        shoppingItem.copy(
                            pricePerItem = value.toDoubleOrNull() ?: 0.0
                        )
                    )
                },
                placeholder = {
                    Text(text = "Price Per Item")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    Timber.d("CLickedddddd .....")
                    viewmodel.AddItem()

                    navController.popBackStack()


                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .width(180.dp),
                shape = RectangleShape, // Explicitly define a rectangular shape
            ) {
                Text(text = "Add")
            }
        }
    }
}


@Preview
@Composable
fun AddShopping() {
    val navController = rememberNavController()
    Scaffold {
//        AddShoppingItemScreen(navController, modifier = Modifier.padding(it))
    }
}