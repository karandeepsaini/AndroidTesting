package com.example.shoppinglisttesting.ui.screens


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shoppinglisttesting.data.ShoppingItem
import com.example.shoppinglisttesting.db.Dao
import com.example.shoppinglisttesting.db.DatabaseModule
import com.example.shoppinglisttesting.network.NetworkModule
import com.example.shoppinglisttesting.ui.NavigationItem
import com.example.shoppinglisttesting.repo.ShoppingRepositoryImpl
import com.example.shoppinglisttesting.ui.viewmodels.MainViewmodel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@Composable
fun ShoppingItemRow(item: ShoppingItem) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        ) {
        AsyncImage(
            model = item.image,
            contentDescription = "Shopping Item Image",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            placeholder = painterResource(id = android.R.drawable.ic_search_category_default),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(text = "${item.amount}x ${item.name}", fontSize = 25.sp)
            Text(text = "₹${item.pricePerItem}", fontSize = 18.sp)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController?,
    modifier: Modifier = Modifier,
    viewmodel: MainViewmodel,
    onClickAdd : (String) -> Unit
) {
    val shoppingItems by viewmodel.shoppingItemList.collectAsStateWithLifecycle()

    // Calculate total cost dynamically
    val totalCost by viewmodel.totalSum.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .semantics { contentDescription = "Home Screen" }
    ) {
        LazyColumn {
            items(shoppingItems, { shoppingItems: ShoppingItem -> shoppingItems.uid }) { item ->


                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewmodel.DeleteItem(item)
                }

                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.White
                                else -> Color.Red
                            }, label = ""
                        )
                        val alignment = Alignment.CenterEnd


                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(20f)),
                            contentAlignment = alignment
                        ) {

                        }
                    },
                    dismissContent = {
                        ShoppingItemRow(item)
                    }
                )
            }
        }
    }

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Cost: $totalCost $",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
            FloatingActionButton(
                modifier = Modifier,
                shape = CircleShape,
                contentColor = Color.Black,
                containerColor = Color.Yellow,
                onClick = {

                    onClickAdd(NavigationItem.Details.route)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "AddItem")
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    val fakeItems = listOf(
        ShoppingItem(uid = 0, name = "Milk", amount = 1, pricePerItem = 25.0),
        ShoppingItem(uid = 1, name = "Bread", amount = 2, pricePerItem = 15.0)
    )
    // Fake DAO for preview
    val fakeDao = object : Dao {
        override fun getAll(): Flow<List<ShoppingItem>> = flowOf(fakeItems)

        override fun insert(shoppingItem: ShoppingItem) {

        }

        override fun delete(shoppingItem: ShoppingItem) {

        }

        override fun getTotalPrice(): Flow<Int> {
            return flowOf(1)
        }


    }

    val shoppingRepository = ShoppingRepositoryImpl(
        DatabaseModule.provideShoppingDao(DatabaseModule.provideDatabase(LocalContext.current)),
        NetworkModule.provideApiService(NetworkModule.provideRetrofit())
    )

    val fakeViewModel = MainViewmodel(shoppingRepository)

    Scaffold {
        HomeScreen(
            navController = null,
            viewmodel = fakeViewModel,
            modifier = Modifier.padding(it),
            onClickAdd = {}
        )
    }
}