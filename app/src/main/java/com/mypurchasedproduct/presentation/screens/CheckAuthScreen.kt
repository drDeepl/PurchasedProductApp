package com.mypurchasedproduct.presentation.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel


val TAG: String = "CheckLocalAuthScreen"
@Composable
fun CheckLocalAuthScreen(
    authViewModel: AuthViewModel
)
{
    Log.d(TAG, "START")
}
