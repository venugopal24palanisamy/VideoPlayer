package com.example.videoplayer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.videoplayer.R
import com.example.videoplayer.utils.MyFunctions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(
    onAboutItemClick: () -> Unit,
    onHomeItemClick: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleMedium,
        )
    }, actions = {
        Icon(
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = "More",
            modifier = Modifier
                .width(25.dp)
                .height(25.dp)
                .clickable { expanded = true },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },

            ) {
            Column(modifier = Modifier.padding(15.dp)) {

                Text(text = "Settings",
                    modifier = Modifier.clickable {
                        MyFunctions.openSettings(context)
                        expanded = false


                    })
                Spacer(modifier = Modifier.height(25.dp))

                Text(text = "Home",
                    modifier = Modifier.clickable {
                        expanded = false
                        onHomeItemClick()
                    })

                Spacer(modifier = Modifier.height(25.dp))
                Text(text = "About", modifier = Modifier.clickable {
                    onAboutItemClick()
                    expanded = false
                })
            }

        }
    })
}