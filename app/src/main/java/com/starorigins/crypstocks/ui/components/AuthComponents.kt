package com.starorigins.crypstocks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starorigins.crypstocks.R
import com.starorigins.crypstocks.ui.theme.PrimaryColor

@Composable
fun LogoContainer(image: Painter) {
    Image(painter = image, contentDescription = "Logo", modifier = Modifier.fillMaxWidth(), alignment = Alignment.Center)
}

@Composable
fun MyTextField(labelValue : String, leadIcon : Painter) {
    var username by remember {
        mutableStateOf("")
    }
    TextField(
        value = username,
        onValueChange = {username = it},
        placeholder = {
            Text(
                text = labelValue,
                color = Color(0xff212121),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            )
        },
        leadingIcon = { Icon(painter = leadIcon, contentDescription = "")},
        colors = TextFieldDefaults.textFieldColors(
            textColor = PrimaryColor,
            backgroundColor = Color.White,
            leadingIconColor = Color.Black,
            focusedIndicatorColor = PrimaryColor,
            focusedLabelColor = PrimaryColor,
            cursorColor = PrimaryColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(14.dp)
            )
        ,
        shape = RoundedCornerShape(14.dp)
    )
}

@Composable
fun ContactField(labelValue : String, leadIcon : Painter) {
    var contact by remember {
        mutableStateOf("")
    }
    TextField(
        value = contact,
        onValueChange = {contact = it},
        placeholder = {
            Text(
                text = labelValue,
                color = Color(0xff212121),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            )
        },
        leadingIcon = { Icon(painter = leadIcon, contentDescription = "")},
        colors = TextFieldDefaults.textFieldColors(
            textColor = PrimaryColor,
            backgroundColor = Color.White,
            leadingIconColor = Color.Black,
            focusedIndicatorColor = PrimaryColor,
            focusedLabelColor = PrimaryColor,
            cursorColor = PrimaryColor,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(14.dp)
            )
        ,
        shape = RoundedCornerShape(14.dp),

    )
}

@Composable
fun MyCheckbox(modifier: Modifier = Modifier) {
    val checkedState = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            modifier = Modifier,
            colors = CheckboxDefaults.colors(
                checkedColor = PrimaryColor,
                uncheckedColor = PrimaryColor
            )
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium)
                ) {append("By accepting, you agree to our ")}
                withStyle(style = SpanStyle(
                    color = Color(0xffff6200),
                    fontSize = 14.sp,fontWeight = FontWeight.Bold)) {append("terms")}
                withStyle(style = SpanStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium)) {append(" and ")}
                withStyle(style = SpanStyle(
                    color = Color(0xffff6200),
                    fontSize = 14.sp,fontWeight = FontWeight.Bold)) {append("conditions")
                }},

        )
    }
}

@Composable
fun RegisterButton() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .clickable { },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PrimaryColor
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(text = "Register", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
    }
}

@Composable
fun RouteLoginScreen() {
    ClickableText(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)) {append("Already have ")}
            withStyle(style = SpanStyle(
                color = Color(0xffff6200),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)) {append("an account")}
            withStyle(style = SpanStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)) {append("?")}},
        onClick = {}
    )
}

@Composable
fun DividerComponent() {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            Modifier
                .border(width = 2.dp, color = Color(0xFFD9D9D9))
                .padding(2.dp)
                .width(122.dp)
                .height(0.dp)
        )
        Text(text = "Or", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Divider(
            Modifier
                .border(width = 2.dp, color = Color(0xFFD9D9D9))
                .padding(2.dp)
                .width(122.dp)
                .height(0.dp)
        )
    }
}

@Composable
fun DirectLoginFacilityRow() {
    Row (modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(14.dp)),
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "google",
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 15.dp,
                            y = 15.dp
                        )
                        .requiredSize(size = 30.dp))
            }
        }
        Spacer(modifier = Modifier.width(24.dp))
        Card(
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(14.dp)),

        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.twitter),
                    contentDescription = "google",
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 15.dp,
                            y = 15.dp
                        )
                        .requiredSize(size = 30.dp))
            }
        }
        Spacer(modifier = Modifier.width(24.dp))
        Card(
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(14.dp))
        ) {
            Box(
                modifier = Modifier
                    .requiredWidth(width = 128.dp)
                    .requiredHeight(height = 60.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.offset(x = 30.dp,y= 15.dp)
                ){
                    Text(
                        text = "Skip ",
                        color = Color(0xff212121),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier)
                    Image(
                        painter = painterResource(id = R.drawable.skip),
                        contentDescription = "Frame",
                        colorFilter = ColorFilter.tint(Color(0xffff6200)),
                        modifier = Modifier
                            .requiredSize(size = 24.dp))
                }

            }
        }
    }
}


@Preview
@Composable
fun DefaultPreviewSystem() {
    androidx.compose.material.Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            MyTextField(labelValue = "Full Name", leadIcon = painterResource(id = R.drawable.profile))
            ContactField(labelValue = "Contact", leadIcon = painterResource(id = R.drawable.contact))
            MyCheckbox()
            DirectLoginFacilityRow()
        }
    }


}
