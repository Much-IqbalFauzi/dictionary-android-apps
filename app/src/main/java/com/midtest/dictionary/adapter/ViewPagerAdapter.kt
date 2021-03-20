package com.midtest.dictionary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.midtest.dictionary.R
import com.midtest.dictionary.model.Menu

class ViewPagerAdapter(val allMenu: List<Menu>, val context: Context): PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater!!.inflate(R.layout.menu_item, container, false)

        val img: ImageView = view.findViewById(R.id.menu_img)
        val title: TextView = view.findViewById(R.id.menu_title)
        val desc: TextView = view.findViewById(R.id.menu_text)

        img.setImageResource(allMenu.get(position).image)
        title.text = allMenu.get(position).title
        desc.text = allMenu.get(position).desc

        container.addView(view, position)
        return view
    }
    override fun getCount(): Int {
        return allMenu.size
    }
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}