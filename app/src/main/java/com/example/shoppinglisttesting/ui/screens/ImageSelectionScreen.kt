package com.example.shoppinglisttesting.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.shoppinglisttesting.ui.viewmodels.MainViewmodel
import com.google.gson.Gson
import timber.log.Timber

@Composable
fun ImageSelectionScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewmodel: MainViewmodel
) {
    val value by mainViewmodel.searchQuery.collectAsStateWithLifecycle();
    val images by mainViewmodel.imageList.collectAsStateWithLifecycle()
    Timber.d("IMAGESS : %s",Gson().toJson(images))
    Column(
        modifier = modifier.semantics { contentDescription = "AddImage Screen" }
    ) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    mainViewmodel.searchItem(it)
                },
                placeholder = {
                    Text(text = "Search Item")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }



        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.padding(vertical = 20.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(images) {

                AsyncImage(
                    model = it.previewUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            mainViewmodel.setImage(it.previewUrl)
                            navController.popBackStack()
                        }
                )
            }

        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun ImageSelectionScreenPreview() {
    val navController = rememberNavController()
    val viewmodel: MainViewmodel = hiltViewModel()
    Scaffold {
        ImageSelectionScreen(
            modifier = Modifier.padding(9.dp),
            navController = navController,
            mainViewmodel = viewmodel
        )
    }
}