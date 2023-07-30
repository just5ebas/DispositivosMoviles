package com.example.dispositivosmoviles.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.addTextChangedListener
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.data.getMarvelCharsDB
import com.example.dispositivosmoviles.logic.marvel_logic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.ui.activities.dataStore
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.data.UserDataStore
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gManager: GridLayoutManager

    private lateinit var rvAdapter: MarvelAdapter

    private var marvelCharItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    private val limit = 5
    private var offset = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(
            layoutInflater,
            container,
            false
        )

        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        gManager = GridLayoutManager(
            requireActivity(),
            2
        )

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch(Dispatchers.IO) {
            getDataStore()
                .collect() {
//                binding.txtFilter.text = it
                    Log.d("UCE", it.name)
                    Log.d("UCE", it.email)
                    Log.d("UCE", it.session)
                }
        }


        /*
        val names = arrayListOf<String>(
            "Ariel",
            "Michael",
            "Erick",
            "Willian",
            "Edwin",
            "Santiago",
            "Layla"
        )

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner,
            names
        )
        */

        chargeDataRVInit(offset, limit)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRv(offset, limit)
            binding.rvSwipe.isRefreshing = false
        }


        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        val v = lmanager.childCount
                        val p = lmanager.findFirstVisibleItemPosition()
                        val t = lmanager.itemCount

                        // v
                        // p es la posicion en la que esta
                        // t es el total de items
                        if ((v + p) >= t) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                //val newItems = JikanAnimeLogic().getAllAnimes()
                                val newItems = MarvelLogic().getAllMarvelChars(
                                    offset,
                                    limit
                                )

                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItem(newItems)
                                    //this@FirstFragment.offset = offset + limit

                                }
                            }
                            offset += limit
                        }
                    }
                }
            }
        )

        binding.txtFilter.addTextChangedListener { filteredText ->
            val newItems = marvelCharItems.filter { items ->
                items.nombre.lowercase().contains(filteredText.toString().lowercase())
            }
            rvAdapter.replaceListAdapter(newItems)
        }

        /*

                binding.txtFilter.setOnEditorActionListener { textView, actionId, keyEvent ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val inputMethodManager =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(binding.txtFilter.windowToken, 0)

                        chargeDataRvAPI(textView.text.toString())

        //                val fragment = SecondFragment()
        //                val args = Bundle().apply { putString("busqueda", textView.text.toString()) }
        //                fragment.arguments = args
        //
        //                requireActivity().supportFragmentManager.beginTransaction()
        //                    .replace(R.id.frm_container, fragment)
        //                    .addToBackStack(null)
        //                    .commit()

                        return@setOnEditorActionListener true
                    }
                    false
                }
        */
    }


    fun sendMarvelItem(item: MarvelChars) { //: Unit {
        //Los intents solo se encuentran en los fragments y en las Activities
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        // Esta forma de enviar informacion es ineficiente cuando se tienen mas atributos
        i.putExtra("name", item)
        //i.putExtra("comic", item.comic)
        //i.putExtra("imagen", item.imagen)
        startActivity(i)
    }

    fun saveMarveltem(item: MarvelChars): Boolean {
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                if (DispositivosMoviles.getDbInstance().marvelDao()
                        .getOneCharacter(item.id) == null
                ) {
                    DispositivosMoviles
                        .getDbInstance()
                        .marvelDao()
                        .insertMarvelChars(listOf(item.getMarvelCharsDB()))
                }
            }
        }
        return true
    }

    private fun getDataStore(): Flow<UserDataStore> {
        return requireActivity().dataStore.data.map { prefs ->
            UserDataStore(
                name = prefs[stringPreferencesKey("usuario")].orEmpty(),
                email = prefs[stringPreferencesKey("email")].orEmpty(),
                session = prefs[stringPreferencesKey("password")].orEmpty()
            )
        }
    }

    // Serializacion: proceso de pasar de un objeto a un string para poder enviarlo por medio de la web
    // Parcelables:   Mucho mas eficiente que la serializacion, pues ejecutan de mejor manera el mismo proceso
    // Serializacion -> Utiliza objetos JSON
    // Parcelables   -> Son mas rapidos pero su implementacion es compleja, afortunadamente existen plugins que nos ayudan

    /*
    fun corroutine() {
        lifecycleScope.launch(Dispatchers.Main) {
            var name = "Ariel" // no tiene ningun efecto de cambio porque son distintos hilos
            // Por medio de dispatchers elegimos un hilo
            name = withContext((Dispatchers.IO)) {
                name = "Michael"
                return@withContext name // De esta forma si se actualiza en el hilo en el que este esa variable
            }

            binding.cardView.radius
        }
    }
    */

    fun chargeDataRvAPI(search: String) {

        lifecycleScope.launch(Dispatchers.Main) {

            // lo que se ejecuta en ese contexto regresa al contexto Main
            marvelCharItems = withContext(Dispatchers.IO) {
                // se usa dispatcher io porque se maneja entrada y salida (consumo de api)
                return@withContext MarvelLogic().getMarvelChars(
                    search,
                    limit
                )
            }

            rvAdapter = MarvelAdapter(
                marvelCharItems,
                { sendMarvelItem(it) },
                { saveMarveltem(it) }
            )

            // por medio del apply le decimos que debe hacer el codigo
            // funciona similar que el with
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }

        }

        this.offset += this.limit

    }

    fun chargeDataRv(offset: Int, limit: Int) {
        //hilo principal
        lifecycleScope.launch(Dispatchers.Main) {
            //relleno la lista en otro hilo y la retorna
            marvelCharItems = withContext(Dispatchers.IO) {
                return@withContext MarvelLogic().getAllMarvelChars(offset, limit)
            }
            rvAdapter = MarvelAdapter(
                marvelCharItems,
                { sendMarvelItem(it) },
                { saveMarveltem(it) }
            )

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager

//                this.layoutManager = gManager
//                gManager.scrollToPositionWithOffset(offset, limit)
            }
            //this@FirstFragment.offset += limit
        }
        this.offset += this.limit
    }

    fun chargeDataRVInit(offset: Int, limit: Int) {
        if (Metodos().isOnline(requireActivity())) {
            lifecycleScope.launch(Dispatchers.Main) {
                //relleno la listaa en otro hilo y retorno
                marvelCharItems = withContext(Dispatchers.IO) {
                    MarvelLogic().getInitChar(offset, limit)
                    //Lo que estaba aqui se debe poner en el LOGIC, Saludos
                }
                rvAdapter = MarvelAdapter(
                    marvelCharItems,
                    { sendMarvelItem(it) },
                    { saveMarveltem(it) }
                )

                //Si hay IO dentro de main no hace falta el with context
                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager

//                    this.layoutManager = gManager
//                    gManager.scrollToPositionWithOffset(offset, limit)
                }
                //this@FirstFragment.offset += limit
                //false es el orden default true es inverso
            }
            this.offset += this.limit
        } else {
            Snackbar.make(binding.cardView, "No hay conexion", Snackbar.LENGTH_LONG).show()
        }
    }

}