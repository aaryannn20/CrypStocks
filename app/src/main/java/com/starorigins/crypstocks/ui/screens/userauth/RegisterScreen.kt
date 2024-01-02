package com.starorigins.crypstocks.ui.screens.userauth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.starorigins.crypstocks.R
import com.starorigins.crypstocks.ui.components.*
import com.starorigins.crypstocks.ui.screens.Screen

@Composable
fun RegisterScreen(registerViewModel: UserAuthViewModel = viewModel(), navController: NavController) {
    Surface(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Spacer(modifier = Modifier.height(10.dp))
            LogoContainer(image = painterResource(id = R.drawable.logo))
            Spacer(modifier = Modifier.height(32.dp))
            MyTextField(
                labelValue = "Email",
                leadIcon = painterResource(id = R.drawable.profile),
                onInput = {
                    registerViewModel.onEvent(UserAuthEvents.EmailChanged(it))
                }
            )
            Spacer(modifier = Modifier.height(28.dp))
            PasswordField(
                labelValue = "Password",
                leadIcon = painterResource(id = R.drawable.password),
                onInput = {
                    registerViewModel.onEvent(UserAuthEvents.PasswordChanged(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyCheckbox()
            Spacer(modifier = Modifier.height(24.dp))
            RegisterButton(
                onButtonClicked = {
                    registerViewModel.onEvent(UserAuthEvents.RegisterButtonClicked)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            RouteLoginScreen(navController)
            Spacer(modifier = Modifier.height(32.dp))
            DividerComponent()
            Spacer(modifier = Modifier.height(32.dp))
            DirectLoginFacilityRow(navController)
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
}