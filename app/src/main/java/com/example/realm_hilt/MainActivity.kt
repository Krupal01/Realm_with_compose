package com.example.realm_hilt

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.realm_hilt.compose.MainScrCompose
import com.example.realm_hilt.model.User
import com.example.realm_hilt.ui.theme.Realm_hiltTheme
import com.example.realm_hilt.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : UserViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .schemaVersion(1L)
                .name("realm_and_compose")
                .build()
        )
        setContent {
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
            val coroutineScope = rememberCoroutineScope()
            Realm_hiltTheme {
                BottomSheetScaffold(
                    sheetContent = {
                        val userName = remember { mutableStateOf("") }
                        val userPhone = remember { mutableStateOf("") }
                        TextField(
                            modifier = Modifier
                                .padding(vertical = 2.dp, horizontal = 3.dp)
                                .fillMaxWidth(),
                            value = userName.value,
                            onValueChange = {
                                userName.value = it
                            },
                            label = {
                                Text(text = "User Name")
                            },
                            placeholder = {
                                Text(text = "User Name")
                            }
                        )
                        TextField(
                            modifier = Modifier
                                .padding(vertical = 2.dp, horizontal = 3.dp)
                                .fillMaxWidth(),
                            value = userPhone.value,
                            onValueChange = {
                                userPhone.value = it
                            },
                            label = {
                                Text(text = "User Phone number")
                            },
                            placeholder = {
                                Text(text = "User Phone number")
                            }
                        )
                        Button(
                            onClick = {
                                viewModel.addUser(User(name = userName.value , phone_number = userPhone.value))
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            },
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 3.dp).fillMaxWidth()
                        ) {
                            Text(text = "Add new User")
                        }
                    },
                    sheetPeekHeight = 0.dp,
                    scaffoldState = bottomSheetScaffoldState,
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            coroutineScope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed){
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }else{
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Add,"")
                        }
                    }
                ) {
                    MainScrCompose(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
