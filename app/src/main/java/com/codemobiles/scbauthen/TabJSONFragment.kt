package com.codemobiles.scbauthen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codemobiles.scbauthen.beans.Youtube
import com.codemobiles.scbauthen.beans.YoutubeBean
import com.thefinestartist.ytpa.utils.YouTubeApp
import kotlinx.android.synthetic.main.fragment_tab_json.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TabJSONFragment : Fragment() {
    val adapter = CustomAdapter(ArrayList<Youtube>())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_tab_json, container, false)

        //--------------แบบที่ 1-------------------//
//       val mRecyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
//        mRecyclerView.layoutManager = LinearLayoutManager(context)
//        mRecyclerView.adapter = adapter

        //--------------แบบที่ 2-------------------//
         _view.recyclerView.let{

             //--------------------recycler layout แนวตั้ง-------------------------//
             it.layoutManager = LinearLayoutManager(context)

             //--------------------recycler layout แนวนอน-----------------------//
             //it.layoutManager = LinearLayoutManager(context,LinearLayout.HORIZONTAL, false)

             //--------------------recycler layout แบบ 2 column----------------//
             //it.layoutManager = GridLayoutManager(context,2)
             it.adapter = adapter
         }


        feed()
        return _view
    }

    private fun feed() {
        val httpClient = HttpClient.create()
        val call = httpClient.feed("admin", "password", "foods")
        call.enqueue(object : Callback<YoutubeBean> {
            override fun onFailure(call: Call<YoutubeBean>, t: Throwable) {

            }

            override fun onResponse(call: Call<YoutubeBean>, response: Response<YoutubeBean>) {

                adapter.mDataArray.addAll(response.body()!!.youtubes)
                adapter.notifyDataSetChanged()
            }

        })
//        val httpClient = HttpClient.create()
//        val call = httpClient.feedType()
//
//        Log.d("network_url_retrofit",call.request().url().toString())
//
//        call.enqueue(object : Callback<List<JsonTest>> {
//            override fun onFailure(call: Call<List<JsonTest>>, t: Throwable) {
//                Log.d("aaa",t.message.toString())
//
//            }
//
//            override fun onResponse(call: Call<List<JsonTest>>, response: Response<List<JsonTest>>) {
//                Log.d("aaa", response.body().toString())
//
//            }
//
//        })
    }


    inner class CustomAdapter(val mDataArray:ArrayList<Youtube>) : RecyclerView.Adapter<CustomViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
            //by layout
            val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_list,null, false)
            return CustomViewHolder(layout)
        }

        override fun getItemCount(): Int {
            //จำนวน row
            return mDataArray.size
        }

        override fun onBindViewHolder(holder: CustomViewHolder, index: Int) {
            //content มีอะไรบ้าง
            val item = mDataArray[index]
            holder.title.text = item.title
            holder.subtitle.text = item.subtitle

            val youtubeUrl = "data:image/jpeg;base64,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"
            val avatarUrl = "https://cf.shopee.co.th/file/1788ecaec17475b2821a476e3f9d794f_tn"
            Glide.with(activity!!).load(item.youtube_image).into(holder.youtube_image)
            Glide.with(activity!!)
                .load(item.avatar_image).apply ( RequestOptions.circleCropTransform() ).into(holder.avatar_image)
            //set tag ให้เวลาทำ init ข้างล่างจะได้รู้ว่าเราจิ้มที่รูปไหน
            holder.youtube_image.setTag(R.id.item_list_youtube_image, item.id)

        }

    }



    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 1 row
        val title = itemView.item_list_title
        val subtitle = itemView.item_list_subtitle
        val avatar_image = itemView.item_list_avatar
        val youtube_image = itemView.item_list_youtube_image

        init {
//            itemView.setOnClickListener {
//                //click row
//            }
            youtube_image.setOnClickListener {
                var id = it.getTag(R.id.item_list_youtube_image) as String
                //Log.d("monika_youtube_id", id)
                YouTubeApp.startVideo(it.context,id)

            }
        }
    }


}
