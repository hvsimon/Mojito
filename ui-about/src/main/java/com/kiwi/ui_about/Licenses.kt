package com.kiwi.ui_about

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.lightColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Licenses(
    navigateUp: () -> Unit,
) {
    MaterialTheme(colors = if (isSystemInDarkTheme()) darkColors() else lightColors()) {

        val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }

        Scaffold(
            topBar = {
                TopBar(
                    navigateUp = navigateUp,
                    scrollBehavior = scrollBehavior,
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            LibrariesContainer(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun TopBar(
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    SmallTopAppBar(
        title = { Text(text = stringResource(id = R.string.licenses)) },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        }
    )
}
