package com.example.dispositivosmoviles.logic.marvel_logic

import android.util.Log
import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndpoint
import com.example.dispositivosmoviles.data.entities.marvel.characters.getMarvelChars
import com.example.dispositivosmoviles.logic.data.MarvelChars

class MarvelLogic {

    suspend fun getMarvelChars(name: String, limit: Int): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()

        val call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        )

        if (call != null) {
            val response = call.getCharactersThatStartWith(name, limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach() {
                    itemList.add(it.getMarvelChars())
                }
            } else {
                Log.d("UCE", response.toString())
            }
        }


        return itemList

    }

    suspend fun getAllMarvelChars(offset: Int, limit: Int): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()

        val call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        )

        if (call != null) {
            val response = call.getAllMarvelChars(offset, limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach() {
                    itemList.add(it.getMarvelChars())
                }
            } else {
                Log.d("UCE", response.toString())
            }
        }

        return itemList

    }

}