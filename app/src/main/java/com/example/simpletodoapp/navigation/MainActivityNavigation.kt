package com.example.simpletodoapp.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpletodoapp.ui.composables.screens.AddNewTaskBottomSheet
import com.example.simpletodoapp.ui.composables.screens.HomeScreen
import com.example.simpletodoapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityNavigation(mainViewModel: MainViewModel) {

    val navController = rememberNavController()
//    val mainViewModel : MainViewModel = viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    val addNewTaskBottomsheetState = rememberModalBottomSheetState()

    NavHost(
        navController = navController, startDestination = NavigationConstants.MainActivityScreens.HOME.name
    ) {
        composable(NavigationConstants.MainActivityScreens.HOME.name) {
            HomeScreen { isDailyTask, title ->
                mainViewModel.addNewTodoItem(isDailyTask, title)
            }
        }
    }
}


