package com.example.bostatechnocalassignment.ui.details


import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.bostatechnocalassignment.databinding.DialogImageViewBinding
import com.squareup.picasso.Picasso


class ImageViewerDialog(
    private val titleString: String?,
) :
    DialogFragment() {
    constructor() : this(null)

    override fun onStart() {
        super.onStart()
        val windowWidth = getWindowWidth()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            (windowWidth * 0.9).toInt()
        )
    }


    private fun getWindowWidth(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = activity?.display
            display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = activity?.windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            display?.getMetrics(displayMetrics)
        }
        return displayMetrics.widthPixels
    }

    private var _binding: DialogImageViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DialogImageViewBinding.inflate(layoutInflater, container, false)
        Picasso.get().load(titleString).into(binding.myZoomImageView)
        binding.shareBtn.setOnClickListener {
            val bitmapDrawable: BitmapDrawable = binding.myZoomImageView.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            val bitmapPath = MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                "some title",
                null
            )
            val bitmapUri = Uri.parse(bitmapPath)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/jpeg"
            shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            startActivity(Intent.createChooser(shareIntent, "Share Image"))
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}