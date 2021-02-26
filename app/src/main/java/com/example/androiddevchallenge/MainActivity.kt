/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.purple200
import com.example.androiddevchallenge.ui.theme.purple500
import com.google.gson.GsonBuilder
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private var puppiesList: List<Puppy> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsePuppyJsonAndConvertAsList()
        setContent {
            MyTheme {
                MyApp(puppiesList)
            }
        }
    }

    @Composable
    fun MyApp(puppiesList: List<Puppy>) {
        Surface(color = MaterialTheme.colors.background) {
            Column(modifier = Modifier.fillMaxSize()) {
                puppyListTitle()
                PuppiesList(puppiesList, ::puppyClickHandler)
            }
        }
    }

    @Composable
    fun puppyListTitle() {
        Text(
            stringResource(R.string.puppy_list),
            Modifier
                .background(purple200)
                .fillMaxWidth()
                .height(56.dp)
                .padding(top = 10.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }

    @Preview("Light Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun LightPreview() {
        MyTheme {
            MyApp(puppiesList)
        }
    }

    @Preview("Dark Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun DarkPreview() {
        MyTheme(darkTheme = true) {
            MyApp(puppiesList)
        }
    }

    private fun parsePuppyJsonAndConvertAsList() {
        val gson = GsonBuilder().create()
        puppiesList = gson.fromJson(loadPuppiesJSONFromAsset(), Array<Puppy>::class.java).toList()
    }

    private fun loadPuppiesJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("puppies.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }

    @Composable
    fun PuppiesList(puppiesList: List<Puppy>, puppyClicked: (Puppy) -> Unit) {
        LazyColumn {
            items(puppiesList) { puppy ->
                Column() {
                    Row(
                        Modifier
                            .clickable {
                                puppyClicked(puppy)
                            }
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        val resId: Int = this@MainActivity.resources.getIdentifier(
                            puppy.image,
                            "drawable",
                            this@MainActivity.packageName
                        )
                        val image: Painter = painterResource(id = resId)
                        Image(

                            painter = image, contentDescription = "",
                            Modifier
                                .height(80.dp)
                                .width(80.dp)
                                .clip(CircleShape)
                                .border(2.dp, purple500, CircleShape),
                            contentScale = ContentScale.FillBounds,
                        )
                        Column {
                            Text(
                                text = puppy.name ?: "",
                                Modifier.padding(start = 5.dp, top = 5.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = puppy.gender ?: "",
                                Modifier.padding(start = 5.dp),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = puppy.location ?: "",
                                Modifier.padding(start = 5.dp),
                                fontSize = 14.sp
                            )
                        }
                    }
                    Divider(Modifier.padding(top = 5.dp), color = Color.Blue, thickness = 2.dp)
                }
            }
        }
    }

    private fun puppyClickHandler(puppy: Puppy) {
        val intent = Intent(this@MainActivity, PuppyDetailsActivity::class.java)
        intent.putExtra("puppyData", puppy)
        startActivity(intent)
    }
}
