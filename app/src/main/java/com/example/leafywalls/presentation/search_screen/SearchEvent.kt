package com.example.leafywalls.presentation.search_screen

import com.example.leafywalls.data.db.History

sealed interface SearchEvent {
    //object FocusSearch: SearchEvent
    data class OnSearch(val query: String): SearchEvent
    data class DeleteHistory(val history: History): SearchEvent
    object ClearHistory: SearchEvent

}