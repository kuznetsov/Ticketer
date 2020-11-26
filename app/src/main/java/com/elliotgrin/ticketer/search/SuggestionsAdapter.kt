package com.elliotgrin.ticketer.search

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.model.CityUiModel
import com.elliotgrin.ticketer.util.inflate
import kotlinx.android.synthetic.main.item_autocomplete_suggestion.view.*

typealias OnSuggestionClickCallback = (CityUiModel) -> Unit

private const val LAYOUT_ID = R.layout.item_autocomplete_suggestion

class SuggestionsAdapter(
    private val autocompleteRequestHelper: AutocompleteRequestHelper,
    private val onSuggestionClick: OnSuggestionClickCallback
) : BaseAdapter(), Filterable {

    private val suggestions = mutableListOf<CityUiModel>()

    override fun getCount() = suggestions.size

    override fun getItem(position: Int) = suggestions[position].fullName

    override fun getItemId(position: Int) = suggestions[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: parent.inflate(LAYOUT_ID)

        val suggestion = suggestions[position]
        view.textViewSuggestion.text = getItem(position)
        view.setOnClickListener { onSuggestionClick(suggestion) }
        return view
    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            TODO("Not yet implemented")
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            TODO("Not yet implemented")
        }
    }

}
