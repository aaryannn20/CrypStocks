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
import com.starorigins.crypstocks.R
import com.starorigins.crypstocks.ui.components.DirectLoginFacilityRow
import com.starorigins.crypstocks.ui.components.DividerComponent
import com.starorigins.crypstocks.ui.components.LoginButton
import com.starorigins.crypstocks.ui.components.LogoContainer
import com.starorigins.crypstocks.ui.components.MyTextField
import com.starorigins.crypstocks.ui.components.PasswordField
import com.starorigins.crypstocks.ui.components.RouteSignUpScreen

@Composable
fun LoginScreen(loginViewModel: UserAuthViewModel = viewModel()) {
    Surface(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Spacer(modifier = Modifier.height(10.dp))
            LogoContainer(image = painterResource(id = R.drawable.logo))
            Spacer(modifier = Modifier.height(32.dp))
            MyTextField(
                labelValue = "Username",
                leadIcon = painterResource(id = R.drawable.profile),
                onInput = {
                    loginViewModel.onEvent(UserAuthEvents.UserNameChanged(it))
                }
            )
            Spacer(modifier = Modifier.height(28.dp))
            PasswordField(
                labelValue = "Password",
                leadIcon = painterResource(id = R.drawable.password),
                onInput = {
                    loginViewModel.onEvent(UserAuthEvents.PasswordChanged(it))
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            LoginButton(onButtonClicked = {
                loginViewModel.onEvent(UserAuthEvents.LoginButtonClicked)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            RouteSignUpScreen()
            Spacer(modifier = Modifier.height(32.dp))
            DividerComponent()
            Spacer(modifier = Modifier.height(32.dp))
            DirectLoginFacilityRow()
        }
    }
}

@Preview
@Composable
fun DefaultLoginScreenPreview() {
    LoginScreen()
}