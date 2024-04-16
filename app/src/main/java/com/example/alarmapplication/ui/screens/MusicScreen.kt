package com.example.alarmapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmapplication.R


@Composable
fun MusicScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()
    ) {
        Text(
            fontWeight = FontWeight.Bold, fontSize = 28.sp, text = "Выберете мелодию"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(120.dp, 80.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.music_fire),
                    contentDescription = "MusicIcon"
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(120.dp, 80.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.music_forest),
                    contentDescription = "ForestIcon"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(120.dp, 80.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.music_water),
                    contentDescription = "WaterIcon"
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(120.dp, 80.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.music_storm),
                    contentDescription = "StormIcon"
                )
            }
        }

    }
}

// TODO: Добавить активацию музыки при нажатии (отключение при повтороном нажатии, музыка на репите. Сверстать калькулятор времени (timePicker мб). Реализовать функционал подсчета