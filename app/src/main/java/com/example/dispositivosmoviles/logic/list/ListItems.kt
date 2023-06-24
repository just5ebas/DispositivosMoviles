package com.example.dispositivosmoviles.logic.list

import android.os.Parcelable
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.marvel.MarvelChars
import kotlinx.parcelize.Parcelize



class ListItems {

    fun returnMarvelList(): List<MarvelChars> {
        val items = listOf(
            MarvelChars(
                1,
                "Spiderman",
                "The Amazing Spiderman (1963)",
                "https://comicvine.gamespot.com/a/uploads/square_medium/11170/111705043/8843794-neverready.jpg"
            ),
            MarvelChars(
                2,
                "Daredevil",
                "Daredevil (1964)",
                "https://comicvine.gamespot.com/a/uploads/square_medium/6/63459/5595347-6645510049-f1ae9.jpg"
            ),
            MarvelChars(
                3,
                "Punisher",
                "The Punisher (1987)",
                "https://comicvine.gamespot.com/a/uploads/square_medium/14/147237/7927302-rco011_1583587732.jpg"
            ),
            MarvelChars(
                4,
                "Deadpool",
                "Deadpool (1997)",
                "https://comicvine.gamespot.com/a/uploads/square_medium/11131/111317321/5939443-5.jpg"
            ), MarvelChars(
                5,
                "Mr. Fantastic",
                "Fantastic Four (1961)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11112/111123579/7316596-fantastic_four_vol_6_1_mr._fantastic_variant_textless.jpg"
            )

        )
        return items
    }

}