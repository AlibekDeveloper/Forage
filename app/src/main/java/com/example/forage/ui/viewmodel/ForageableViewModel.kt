package com.example.forage.ui.viewmodel

import androidx.lifecycle.*
import com.example.forage.data.ForageableDao
import com.example.forage.model.Forageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Shared [ViewModel] to provide data to the [ForageableListFragment], [ForageableDetailFragment],
 * and [AddForageableFragment] and allow for interaction the the [ForageableDao]
 */

// TODO: pass a ForageableDao value as a parameter to the view model constructor
class ForageableViewModel(
    private val forageableDao: ForageableDao
): ViewModel() {

    // TODO: create a property to set to a list of all forageables from the DAO
    val forageablesList: LiveData<List<Forageable>> = forageableDao.getForageables().asLiveData()

    // TODO : create method that takes id: Long as a parameter and retrieve a Forageable from the
    fun getForageableLD(id: Long): LiveData<Forageable> {
        return forageableDao.getForageable(id).asLiveData()
    }

    //  database by id via the DAO.

    fun addForageable(
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )

        viewModelScope.launch {
            forageableDao.insert(forageable)
        }
    }

    fun updateForageable(
        id: Long,
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            id = id,
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )
        viewModelScope.launch(Dispatchers.IO) {
            forageableDao.update(forageable)
        }
    }

    fun deleteForageable(forageable: Forageable) {
        viewModelScope.launch(Dispatchers.IO) {
            forageableDao.delete(forageable)
        }
    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }
}

// TODO: create a view model factory that takes a ForageableDao as a property and
//  creates a ForageableViewModel
class ViewModelFactory(private val forageableDao: ForageableDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ForageableViewModel(forageableDao) as T
    }
}