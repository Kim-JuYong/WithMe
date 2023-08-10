package com.bonobono.presentation.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bonobono.presentation.ui.common.text.CustomTextStyle.primaryColorBtnText
import com.bonobono.presentation.ui.theme.PrimaryBlue
import com.bonobono.presentation.ui.theme.TextGray

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    hint: String,
    onValueChange: (String) -> Unit,
    onFocusChange: () -> Unit
) {
    BasicTextField(
        modifier = Modifier.fillMaxWidth()
            .onFocusChanged { onFocusChange() },
        value = text,
        onValueChange = onValueChange,
        textStyle = textStyle,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            if (text.isEmpty()) {
                Text(
                    text = hint,
                    style = textStyle.copy(color = TextGray)
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun BasicTextField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            focusedTextColor = LightGray,
//            unfocusedTextColor = LightGray,
//            focusedBorderColor = PrimaryBlue,
//            unfocusedBorderColor = LightGray
//        ),
        placeholder = { Text(text = hint) },
        shape = RoundedCornerShape(6.dp)
    )
}

@Composable
fun TextFieldWithButton(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes buttonTxt: Int,
    hint: String,
    action: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 16.dp, 0.dp)
                .weight(2f),
            value = value,
            placeholder = { Text(text = hint) },
            onValueChange = onValueChange,
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                textColor = TextGray,
//                focusedBorderColor = PrimaryBlue,
//                unfocusedBorderColor = LightGray
//            )
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(6.dp),
            onClick = { action }) {
            Text(
                text = stringResource(buttonTxt),
                style = primaryColorBtnText,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ProfileEditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean
) {
//    TextField(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        value = value,
//        readOnly = readOnly,
//        onValueChange = onValueChange)
}
