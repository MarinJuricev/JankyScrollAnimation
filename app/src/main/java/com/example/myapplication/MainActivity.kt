package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BudgetScroll()
                }
            }
        }
    }
}

@Composable
fun BudgetScroll(
    listState: LazyListState = rememberLazyListState(),
) {
    val bottomPadding by
        animateDpAsState(
            listState.layoutInfo
                .visibleItemsInfo
                .any { it.index == 24 }.let { _isLastIndexVisible ->
                    if (_isLastIndexVisible) {
                        val layoutInfo = listState.layoutInfo
                        return@let 8.dp + (LocalConfiguration.current.screenHeightDp.dp - (layoutInfo.visibleItemsInfo.lastOrNull()?.offset?.pxToDp() ?: 0.dp))
                    } else {
                        return@let 8.dp
                    }
                }
        )
    Box {
        LazyColumn(
            state = listState,
        ) {
            items(25, key = { it }) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = index.toString(),
                    )
                }
            }
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = bottomPadding),
            onClick = { /*TODO*/ }) {

        }
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }