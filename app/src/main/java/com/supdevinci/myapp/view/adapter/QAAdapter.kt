package com.supdevinci.myapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.supdevinci.myapp.R

class QAAdapter(
    private var responses: List<String>,
    private val correctResponse: String,
    private val onResponseClick: (String) -> Unit
) : RecyclerView.Adapter<QAAdapter.ResponseViewHolder>() {

    private var isAnswered = false
    private val holders = mutableListOf<ResponseViewHolder>()

    class ResponseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val responseButton: AppCompatButton = view.findViewById(R.id.response)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reponse, parent, false)
        val holder = ResponseViewHolder(view)
        holders.add(holder)
        return holder
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        val responseText = responses[position]
        holder.responseButton.text = responseText

        holder.responseButton.setOnClickListener {
            if (!isAnswered) {
                isAnswered = true
                if (responseText == correctResponse) {
                    holder.responseButton.background = AppCompatResources.getDrawable(
                        holder.itemView.context,
                        R.drawable.button_background_green
                    )
                } else {
                    holder.responseButton.background = AppCompatResources.getDrawable(
                        holder.itemView.context,
                        R.drawable.button_background_red
                    )
                    highlightCorrectAnswer()
                }

                onResponseClick(responseText)
            }
        }
    }

    override fun getItemCount(): Int = responses.size

    fun updateData(newResponses: List<String>) {
        responses = newResponses
        isAnswered = false
        holders.clear()
        notifyDataSetChanged()
    }

    private fun highlightCorrectAnswer() {
        holders.forEach { holder ->
            if (holder.responseButton.text == correctResponse) {
                holder.responseButton.background = AppCompatResources.getDrawable(
                    holder.itemView.context,
                    R.drawable.button_background_green
                )
            }
        }
    }
}
