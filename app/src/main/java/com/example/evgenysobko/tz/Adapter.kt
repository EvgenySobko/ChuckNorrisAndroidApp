package com.example.evgenysobko.tz
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class Adapter(private val jokes: List<Joke>?) : RecyclerView.Adapter<Adapter.JokeViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): JokeViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.joke_layout, viewGroup, false)
        return JokeViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: JokeViewHolder, i: Int) {
        viewHolder.bind(jokes!![i])
    }

    override fun getItemCount(): Int {
        return jokes!!.size
    }

    class JokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.text_of_joke)

        fun bind(jokes: Joke) {
            nameTextView.text = jokes.joke
        }

    }

}