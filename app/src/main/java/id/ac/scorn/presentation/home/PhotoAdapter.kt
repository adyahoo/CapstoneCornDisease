package id.ac.scorn.presentation.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.scorn.databinding.ItemPhotoBinding
import id.ac.scorn.domain.Photo

class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    private var listData = ArrayList<Photo>()
    var onItemClick: ((Photo) -> Unit)? = null

    fun setData(photoList: List<Photo>) {
        this.listData.clear()
        this.listData.addAll(photoList)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(Uri.parse(photo.photo))
                    .into(imgPhoto)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemBinding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}