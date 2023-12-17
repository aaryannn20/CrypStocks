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
import com.starorigins.crypstocks.R
import com.starorigins.crypstocks.ui.components.ContactField
import com.starorigins.crypstocks.ui.components.DirectLoginFacilityRow
import com.starorigins.crypstocks.ui.components.DividerComponent
import com.starorigins.crypstocks.ui.components.LogoContainer
import com.starorigins.crypstocks.ui.components.MyCheckbox
import com.starorigins.crypstocks.ui.components.MyTextField
import com.starorigins.crypstocks.ui.components.RegisterButton
import com.starorigins.crypstocks.ui.components.RouteLoginScreen

@Composable
fun RegisterScreen() {
    Surface(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Spacer(modifier = Modifier.height(10.dp))
            LogoContainer(image = painterResource(id = R.drawable.logo))
            Spacer(modifier = Modifier.height(32.dp))
            MyTextField(labelValue = "Full name", leadIcon = painterResource(id = R.drawable.profile))
            Spacer(modifier = Modifier.height(28.dp))
            ContactField(labelValue = "Contact", leadIcon = painterResource(id = R.drawable.contact))
            Spacer(modifier = Modifier.height(16.dp))
            MyCheckbox()
            Spacer(modifier = Modifier.height(24.dp))
            RegisterButton()
            Spacer(modifier = Modifier.height(16.dp))
            RouteLoginScreen()
            Spacer(modifier = Modifier.height(32.dp))
            DividerComponent()
            Spacer(modifier = Modifier.height(32.dp))
            DirectLoginFacilityRow()
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}