package com.elliotgrin.ticketer.search

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.model.CityUiModel
import com.elliotgrin.ticketer.util.inflate
import com.github.ajalt.timberkt.d
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
            d { "performFiltering($constraint)" }

            val result = FilterResults()

            // Check whether user has typed more then 2 symbols before making request
            if (constraint == null || constraint.length < 2) return result

            val suggestions = autocompleteRequestHelper.requestCity(constraint.toString())

            result.values = suggestions
            result.count = suggestions.size

            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.count > 0) {
                setSuggestions(results.values)
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }

    private fun setSuggestions(values: Any) {
        val valuesList = values as List<*>
        suggestions.clear()
        valuesList.forEach { value ->
            if (value is CityUiModel) suggestions.add(value)
        }
    }

}
