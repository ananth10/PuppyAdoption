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

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Puppy(
    @SerializedName("about")
    val about: String? = null,
    @SerializedName("age")
    val age: Int? = null,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("health")
    val health: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("owner")
    val owner: String? = null,
    @SerializedName("size")
    val size: String? = null
) : Parcelable
